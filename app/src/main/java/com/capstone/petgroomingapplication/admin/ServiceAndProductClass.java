package com.capstone.petgroomingapplication.admin;

public class ServiceAndProductClass {

        private String code;
        private String description;
        private String category;
        private String price;

        public ServiceAndProductClass(String code, String description, String category, String price) {
            this.code = code;
            this.description = description;
            this.category = category;
            this.price = price;
        }

        // Getters and Setters
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
