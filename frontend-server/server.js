'use strict';

const http = require('node:http');
const fs = require('node:fs');
const path = require('node:path');
const { URL } = require('node:url');

const PORT = Number(process.env.PORT || 3000);
const BACKEND_URL = new URL(process.env.BACKEND_URL || 'http://localhost:8080');
const PUBLIC_DIR = path.join(__dirname, 'public');
const MIME_TYPES = {
  '.html': 'text/html; charset=utf-8',
  '.css': 'text/css; charset=utf-8',
  '.js': 'text/javascript; charset=utf-8',
  '.json': 'application/json; charset=utf-8',
  '.svg': 'image/svg+xml',
  '.png': 'image/png',
  '.jpg': 'image/jpeg',
  '.ico': 'image/x-icon'
};

function proxyApi(req, res, requestUrl) {
  const options = {
    protocol: BACKEND_URL.protocol,
    hostname: BACKEND_URL.hostname,
    port: BACKEND_URL.port || (BACKEND_URL.protocol === 'https:' ? 443 : 80),
    method: req.method,
    path: requestUrl.pathname + requestUrl.search,
    headers: { ...req.headers, host: BACKEND_URL.host }
  };

  const proxyRequest = (BACKEND_URL.protocol === 'https:' ? require('node:https') : http)
    .request(options, (proxyResponse) => {
      res.writeHead(proxyResponse.statusCode || 502, proxyResponse.headers);
      proxyResponse.pipe(res);
    });

  proxyRequest.on('error', (error) => {
    console.error(`Backend proxy error: ${error.message}`);
    if (!res.headersSent) {
      res.writeHead(502, { 'Content-Type': 'application/json; charset=utf-8' });
    }
    res.end(JSON.stringify({
      message: `Не удалось подключиться к backend (${BACKEND_URL.origin}). Запусти Spring Boot и проверь BACKEND_URL.`
    }));
  });

  req.pipe(proxyRequest);
}

const server = http.createServer((req, res) => {
  const requestUrl = new URL(req.url, `http://${req.headers.host || 'localhost'}`);

  if (requestUrl.pathname === '/health') {
    res.writeHead(200, { 'Content-Type': 'application/json; charset=utf-8' });
    res.end(JSON.stringify({ status: 'ok', frontend: true, backend: BACKEND_URL.origin }));
    return;
  }

  if (requestUrl.pathname === '/api' || requestUrl.pathname.startsWith('/api/')) {
    proxyApi(req, res, requestUrl);
    return;
  }

  let requestedPath = decodeURIComponent(requestUrl.pathname);
  if (requestedPath === '/') requestedPath = '/index.html';
  const filePath = path.resolve(PUBLIC_DIR, `.${requestedPath}`);
  if (!filePath.startsWith(PUBLIC_DIR + path.sep) && filePath !== path.join(PUBLIC_DIR, 'index.html')) {
    res.writeHead(403);
    res.end('Forbidden');
    return;
  }

  fs.readFile(filePath, (error, content) => {
    if (error) {
      res.writeHead(error.code === 'ENOENT' ? 404 : 500, { 'Content-Type': 'text/plain; charset=utf-8' });
      res.end(error.code === 'ENOENT' ? 'Not found' : 'Internal server error');
      return;
    }
    res.writeHead(200, {
      'Content-Type': MIME_TYPES[path.extname(filePath).toLowerCase()] || 'application/octet-stream',
      'X-Content-Type-Options': 'nosniff'
    });
    res.end(content);
  });
});

server.listen(PORT, '0.0.0.0', () => {
  console.log(`UniTeam frontend: http://localhost:${PORT}`);
  console.log(`API proxy target: ${BACKEND_URL.origin}`);
  console.log('Backend must be started separately.');
});
