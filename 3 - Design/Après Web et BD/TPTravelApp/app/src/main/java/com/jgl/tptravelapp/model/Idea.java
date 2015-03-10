package com.jgl.tptravelapp.model;

/**
 * Entité Idée
 */
public class Idea {

    private String ideaName;
    private int ideaDrawableId;
    private String ideaDrawableUrl;
    private String ideaDescription;
    private double latitude;
    private double longitude;

    public Idea(String ideaName, int ideaDrawableId, String ideaDescription, double latitude, double longitude) {
        this.ideaName = ideaName;
        this.ideaDrawableId = ideaDrawableId;
        this.ideaDescription = ideaDescription;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Idea(String ideaName, String ideaDrawableUrl, String ideaDescription, double latitude, double longitude) {
        this.ideaName = ideaName;
        this.ideaDrawableUrl = ideaDrawableUrl;
        this.ideaDescription = ideaDescription;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getIdeaName() {
        return ideaName;
    }

    public void setIdeaName(String ideaName) {
        this.ideaName = ideaName;
    }

    public int getIdeaDrawableId() {
        return ideaDrawableId;
    }

    public void setIdeaDrawableId(int ideaDrawableId) {
        this.ideaDrawableId = ideaDrawableId;
    }

    public String getIdeaDescription() {
        return ideaDescription;
    }

    public void setIdeaDescription(String ideaDescription) {
        this.ideaDescription = ideaDescription;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getIdeaDrawableUrl() {
        return ideaDrawableUrl;
    }

    public void setIdeaDrawableUrl(String ideaDrawableUrl) {
        this.ideaDrawableUrl = ideaDrawableUrl;
    }
}
