package com.test.placesapp.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class PlacesResponseModel {

    @Expose
    private String type;
    @Expose
    private Block block;
    @Expose
    private Boolean success;

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The block
     */
    public Block getBlock() {
        return block;
    }

    /**
     * @param block The block
     */
    public void setBlock(Block block) {
        this.block = block;
    }

    /**
     * @return The success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * @param success The success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public class Block {
        @Expose
        private Boolean hasMore;
        @Expose
        private String cmd;
        @Expose
        private List<PlaceModel> items = new ArrayList<PlaceModel>();
        @Expose
        private String service;
        @Expose
        private Integer offset;

        /**
         * @return The hasMore
         */
        public Boolean getHasMore() {
            return hasMore;
        }

        /**
         * @param hasMore The hasMore
         */
        public void setHasMore(Boolean hasMore) {
            this.hasMore = hasMore;
        }

        /**
         * @return The cmd
         */
        public String getCmd() {
            return cmd;
        }

        /**
         * @param cmd The cmd
         */
        public void setCmd(String cmd) {
            this.cmd = cmd;
        }

        /**
         * @return The items
         */
        public List<PlaceModel> getItems() {
            return items;
        }

        /**
         * @param items The items
         */
        public void setItems(List<PlaceModel> items) {
            this.items = items;
        }

        /**
         * @return The service
         */
        public String getService() {
            return service;
        }

        /**
         * @param service The service
         */
        public void setService(String service) {
            this.service = service;
        }

        /**
         * @return The offset
         */
        public Integer getOffset() {
            return offset;
        }

        /**
         * @param offset The offset
         */
        public void setOffset(Integer offset) {
            this.offset = offset;
        }
    }

    public class PlaceModel {
        @Expose
        private String id;
        @Expose
        private String subtext;
        @Expose
        private String category;
        @Expose
        private String title;
        @Expose
        private Integer price;
        @Expose
        private Location location;
        @Expose
        private String name;
        @Expose
        private String image;
        @Expose
        private Rating rating;

        /**
         * @return The id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id The id
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return The subtext
         */
        public String getSubtext() {
            return subtext;
        }

        /**
         * @param subtext The subtext
         */
        public void setSubtext(String subtext) {
            this.subtext = subtext;
        }

        /**
         * @return The category
         */
        public String getCategory() {
            return category;
        }

        /**
         * @param category The category
         */
        public void setCategory(String category) {
            this.category = category;
        }

        /**
         * @return The title
         */
        public String getTitle() {
            return title;
        }

        /**
         * @param title The title
         */
        public void setTitle(String title) {
            this.title = title;
        }

        /**
         * @return The price
         */
        public Integer getPrice() {
            return price;
        }

        /**
         * @param price The price
         */
        public void setPrice(Integer price) {
            this.price = price;
        }

        /**
         * @return The location
         */
        public Location getLocation() {
            return location;
        }

        /**
         * @param location The location
         */
        public void setLocation(Location location) {
            this.location = location;
        }

        /**
         * @return The name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name The name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return The image
         */
        public String getImage() {
            return image;
        }

        /**
         * @param image The image
         */
        public void setImage(String image) {
            this.image = image;
        }

        /**
         * @return The rating
         */
        public Rating getRating() {
            return rating;
        }

        /**
         * @param rating The rating
         */
        public void setRating(Rating rating) {
            this.rating = rating;
        }

    }

    public class Location {
        @Expose
        private String region;
        @Expose
        private Double distance;
        @Expose
        private String location;
        @Expose
        private String postalCode;
        @Expose
        private String street;
        @Expose
        private Double longitude;
        @Expose
        private Double latitude;
        @Expose
        private String countryCode;
        @Expose
        private String country;
        @Expose
        private String city;

        /**
         * @return The region
         */
        public String getRegion() {
            return region;
        }

        /**
         * @param region The region
         */
        public void setRegion(String region) {
            this.region = region;
        }

        /**
         * @return The distance
         */
        public Double getDistance() {
            return distance;
        }

        /**
         * @param distance The distance
         */
        public void setDistance(Double distance) {
            this.distance = distance;
        }

        /**
         * @return The location
         */
        public String getLocation() {
            return location;
        }

        /**
         * @param location The location
         */
        public void setLocation(String location) {
            this.location = location;
        }

        /**
         * @return The postalCode
         */
        public String getPostalCode() {
            return postalCode;
        }

        /**
         * @param postalCode The postal_code
         */
        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        /**
         * @return The street
         */
        public String getStreet() {
            return street;
        }

        /**
         * @param street The street
         */
        public void setStreet(String street) {
            this.street = street;
        }

        /**
         * @return The longitude
         */
        public Double getLongitude() {
            return longitude;
        }

        /**
         * @param longitude The longitude
         */
        public void setLongitude(Double longitude) {
            this.longitude = longitude;
        }

        /**
         * @return The latitude
         */
        public Double getLatitude() {
            return latitude;
        }

        /**
         * @param latitude The latitude
         */
        public void setLatitude(Double latitude) {
            this.latitude = latitude;
        }

        /**
         * @return The countryCode
         */
        public String getCountryCode() {
            return countryCode;
        }

        /**
         * @param countryCode The country_code
         */
        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

        /**
         * @return The country
         */
        public String getCountry() {
            return country;
        }

        /**
         * @param country The country
         */
        public void setCountry(String country) {
            this.country = country;
        }

        /**
         * @return The city
         */
        public String getCity() {
            return city;
        }

        /**
         * @param city The city
         */
        public void setCity(String city) {
            this.city = city;
        }
    }

    public class Rating {
        @Expose
        private String count;
        @Expose
        private Double fsValue;
        @Expose
        private String type;
        @Expose
        private String text;

        /**
         * @return The count
         */
        public String getCount() {
            return count;
        }

        /**
         * @param count The count
         */
        public void setCount(String count) {
            this.count = count;
        }

        /**
         * @return The fsValue
         */
        public Double getFsValue() {
            return fsValue;
        }

        /**
         * @param fsValue The fsValue
         */
        public void setFsValue(Double fsValue) {
            this.fsValue = fsValue;
        }

        /**
         * @return The type
         */
        public String getType() {
            return type;
        }

        /**
         * @param type The type
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * @return The text
         */
        public String getText() {
            return text;
        }

        /**
         * @param text The text
         */
        public void setText(String text) {
            this.text = text;
        }

    }
}
