package ru.uniteam.models;

import java.util.HashMap;
import java.util.Map;

public enum University {

    MSU(1, "Московский государственный университет имени М. В. Ломоносова"),
    SPBU(2, "Санкт-Петербургский государственный университет"),
    MIPT(3, "Московский физико-технический институт"),
    HSE(4, "Национальный исследовательский университет «Высшая школа экономики»"),
    MSTU_BAUMAN(5, "Московский государственный технический университет имени Н. Э. Баумана"),
    MEPHI(6, "Национальный исследовательский ядерный университет «МИФИ»"),
    ITMO(7, "Национальный исследовательский университет ИТМО"),
    NSU(8, "Новосибирский национальный исследовательский государственный университет"),
    TSU(9, "Томский государственный университет"),
    KFU(10, "Казанский (Приволжский) федеральный университет"),
    URJU(11, "Уральский федеральный университет имени Б. Н. Ельцина"),
    RUDN(12, "Российский университет дружбы народов"),
    MGIMO(13, "Московский государственный институт международных отношений"),
    MISIS(14, "Национальный исследовательский технологический университет «МИСиС»"),
    MAI(15, "Московский авиационный институт"),
    SPBSTU(16, "Санкт-Петербургский политехнический университет Петра Великого"),
    RSSU(17, "Российский государственный социальный университет"),
    PSU(18, "Пермский государственный национальный исследовательский университет"),
    SFU(19, "Сибирский федеральный университет"),
    SUSU(20, "Южно-Уральский государственный университет");

    private final int universityId;
    private final String title;

    private static final Map<Integer, University> BY_ID = new HashMap<>();
    private static final Map<String, University> BY_TITLE = new HashMap<>();

    static {
        for (University u : values()) {
            BY_ID.put(u.universityId, u);
            BY_TITLE.put(u.title.toLowerCase(), u);
        }
    }

    University(int universityId, String title) {
        this.universityId = universityId;
        this.title = title;
    }

    public int getUniversityId() {
        return universityId;
    }

    public String getTitle() {
        return title;
    }

    public static University findById(int id) {
        return BY_ID.get(id);
    }

    public static University findByTitle(String title) {
        if (title == null) return null;
        return BY_TITLE.get(title.toLowerCase());
    }

    public static University findByTitleContains(String part) {
        if (part == null) return null;
        String lower = part.toLowerCase();
        for (University u : values()) {
            if (u.title.toLowerCase().contains(lower)) {
                return u;
            }
        }
        return null;
    }
}