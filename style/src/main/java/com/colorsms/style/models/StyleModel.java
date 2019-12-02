package com.colorsms.style.models;

public class StyleModel {
    private int id;
    private String name;
    private int background;
    private int styleColor;
    private int avatarFrameResource;
    private int avatarGravity;
    private int[] avatarHomeContentPadding;
    private int[] avatarChatContentPadding;
    private int avatarContentColor;
    private int unReadColor;

    private int smsHomeColor;

    private int bbChatInboxResource;
    private int bbChatInboxColor;
    private int bbChatInboxTextColor;
    private int bbChatSendResource;
    private int bbChatSendColor;
    private int bbChatSendTextColor;

    private int preview;
    private int titleColor;

    public StyleModel(int id, String name, int background, int styleColor, int avatarFrameResource, int avatarGravity, int[] avatarHomeContentPadding, int[] avatarChatContentPadding, int avatarContentColor, int unReadColor, int smsHomeColor, int bbChatInboxResource, int bbChatInboxColor, int bbChatInboxTextColor, int bbChatSendResource, int bbChatSendColor, int bbChatSendTextColor, int preview, int titleColor) {
        this.id = id;
        this.name = name;
        this.background = background;
        this.styleColor = styleColor;
        this.avatarFrameResource = avatarFrameResource;
        this.avatarGravity = avatarGravity;
        this.avatarHomeContentPadding = avatarHomeContentPadding;
        this.avatarChatContentPadding = avatarChatContentPadding;
        this.avatarContentColor = avatarContentColor;
        this.unReadColor = unReadColor;
        this.smsHomeColor = smsHomeColor;
        this.bbChatInboxResource = bbChatInboxResource;
        this.bbChatInboxColor = bbChatInboxColor;
        this.bbChatInboxTextColor = bbChatInboxTextColor;
        this.bbChatSendResource = bbChatSendResource;
        this.bbChatSendColor = bbChatSendColor;
        this.bbChatSendTextColor = bbChatSendTextColor;
        this.preview = preview;
        this.titleColor = titleColor;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public int getPreview() {
        return preview;
    }

    public void setPreview(int preview) {
        this.preview = preview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getStyleColor() {
        return styleColor;
    }

    public void setStyleColor(int styleColor) {
        this.styleColor = styleColor;
    }

    public int getAvatarFrameResource() {
        return avatarFrameResource;
    }

    public void setAvatarFrameResource(int avatarFrameResource) {
        this.avatarFrameResource = avatarFrameResource;
    }

    public int getAvatarGravity() {
        return avatarGravity;
    }

    public void setAvatarGravity(int avatarGravity) {
        this.avatarGravity = avatarGravity;
    }

    public int[] getAvatarHomeContentPadding() {
        return avatarHomeContentPadding;
    }

    public void setAvatarHomeContentPadding(int[] avatarHomeContentPadding) {
        this.avatarHomeContentPadding = avatarHomeContentPadding;
    }

    public int[] getAvatarChatContentPadding() {
        return avatarChatContentPadding;
    }

    public void setAvatarChatContentPadding(int[] avatarChatContentPadding) {
        this.avatarChatContentPadding = avatarChatContentPadding;
    }

    public int getAvatarContentColor() {
        return avatarContentColor;
    }

    public void setAvatarContentColor(int avatarContentColor) {
        this.avatarContentColor = avatarContentColor;
    }

    public int getUnReadColor() {
        return unReadColor;
    }

    public void setUnReadColor(int unReadColor) {
        this.unReadColor = unReadColor;
    }

    public int getSmsHomeColor() {
        return smsHomeColor;
    }

    public void setSmsHomeColor(int smsHomeColor) {
        this.smsHomeColor = smsHomeColor;
    }

    public int getBbChatInboxResource() {
        return bbChatInboxResource;
    }

    public void setBbChatInboxResource(int bbChatInboxResource) {
        this.bbChatInboxResource = bbChatInboxResource;
    }

    public int getBbChatInboxColor() {
        return bbChatInboxColor;
    }

    public void setBbChatInboxColor(int bbChatInboxColor) {
        this.bbChatInboxColor = bbChatInboxColor;
    }

    public int getBbChatInboxTextColor() {
        return bbChatInboxTextColor;
    }

    public void setBbChatInboxTextColor(int bbChatInboxTextColor) {
        this.bbChatInboxTextColor = bbChatInboxTextColor;
    }

    public int getBbChatSendResource() {
        return bbChatSendResource;
    }

    public void setBbChatSendResource(int bbChatSendResource) {
        this.bbChatSendResource = bbChatSendResource;
    }

    public int getBbChatSendColor() {
        return bbChatSendColor;
    }

    public void setBbChatSendColor(int bbChatSendColor) {
        this.bbChatSendColor = bbChatSendColor;
    }

    public int getBbChatSendTextColor() {
        return bbChatSendTextColor;
    }

    public void setBbChatSendTextColor(int bbChatSendTextColor) {
        this.bbChatSendTextColor = bbChatSendTextColor;
    }
}
