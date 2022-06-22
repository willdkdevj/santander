/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infotera.santander.getnet.model.sdk;

/**
 *
 * @author danilo
 */
public class Device {

    private String ip_address;
    private String device_id;

    private String http_browser_color_depth;
    private String http_browser_java_enabled;
    private String http_browser_java_script_enabled;
    private String http_browser_language;
    private String http_browser_screen_height;
    private String http_browser_screen_width;
    private String http_browser_time_difference;

    public Device() {
    }

    public Device(String ip_address, String device_id) {
        this.ip_address = ip_address;
        this.device_id = device_id;
    }

    public Device(String ip_address, String http_browser_color_depth, String http_browser_java_enabled, String http_browser_java_script_enabled, String http_browser_language, String http_browser_screen_height, String http_browser_screen_width, String http_browser_time_difference) {
        this.ip_address = ip_address;
        this.http_browser_color_depth = http_browser_color_depth;
        this.http_browser_java_enabled = http_browser_java_enabled;
        this.http_browser_java_script_enabled = http_browser_java_script_enabled;
        this.http_browser_language = http_browser_language;
        this.http_browser_screen_height = http_browser_screen_height;
        this.http_browser_screen_width = http_browser_screen_width;
        this.http_browser_time_difference = http_browser_time_difference;
    }

    public String getHttp_browser_color_depth() {
        return http_browser_color_depth;
    }

    public void setHttp_browser_color_depth(String http_browser_color_depth) {
        this.http_browser_color_depth = http_browser_color_depth;
    }

    public String getHttp_browser_java_enabled() {
        return http_browser_java_enabled;
    }

    public void setHttp_browser_java_enabled(String http_browser_java_enabled) {
        this.http_browser_java_enabled = http_browser_java_enabled;
    }

    public String getHttp_browser_java_script_enabled() {
        return http_browser_java_script_enabled;
    }

    public void setHttp_browser_java_script_enabled(String http_browser_java_script_enabled) {
        this.http_browser_java_script_enabled = http_browser_java_script_enabled;
    }

    public String getHttp_browser_language() {
        return http_browser_language;
    }

    public void setHttp_browser_language(String http_browser_language) {
        this.http_browser_language = http_browser_language;
    }

    public String getHttp_browser_screen_height() {
        return http_browser_screen_height;
    }

    public void setHttp_browser_screen_height(String http_browser_screen_height) {
        this.http_browser_screen_height = http_browser_screen_height;
    }

    public String getHttp_browser_screen_width() {
        return http_browser_screen_width;
    }

    public void setHttp_browser_screen_width(String http_browser_screen_width) {
        this.http_browser_screen_width = http_browser_screen_width;
    }

    public String getHttp_browser_time_difference() {
        return http_browser_time_difference;
    }

    public void setHttp_browser_time_difference(String http_browser_time_difference) {
        this.http_browser_time_difference = http_browser_time_difference;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

}
