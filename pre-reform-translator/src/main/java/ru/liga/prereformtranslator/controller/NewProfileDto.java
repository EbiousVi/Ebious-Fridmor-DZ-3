package ru.liga.prereformtranslator.controller;


import java.util.List;


public class NewProfileDto {
    private Long chatId;
    private String name;
    private String description;
    private Sex sex;
    private List<String> preferences;

    public NewProfileDto(Long chatId, String name, String description, Sex sex, List<String> preferences) {
        this.chatId = chatId;
        this.name = name;
        this.description = description;
        this.sex = sex;
        this.preferences = preferences;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<String> preferences) {
        this.preferences = preferences;
    }
}
