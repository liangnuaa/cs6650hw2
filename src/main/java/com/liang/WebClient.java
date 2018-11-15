package com.liang;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

public class WebClient {
    private String REST_URI;
    private Client client = ClientBuilder.newClient();

    public WebClient(String uri) {
        this.REST_URI = uri;
    }

    public String postStepCount(int userId, int dayId, int timeInterval, int stepCount){
        String postStepCountURI = REST_URI + "/" + userId + "/" + dayId + "/" + timeInterval + "/" + stepCount;
        return client.target(postStepCountURI)
                .request(MediaType.TEXT_PLAIN)
                .post(Entity.entity("", MediaType.TEXT_PLAIN), String.class);
    }

    public int getSingleDay(int userId, int dayId){
        String getSingleDayURI = REST_URI + "/single/" + userId + "/" + dayId;
        return client.target(getSingleDayURI)
                .request()
                .get(Integer.class);
    }

    public int getCurrentDay(int userId){
        String getCurrentDayURI = REST_URI + "/current/" + userId;
        return client.target(getCurrentDayURI)
                .request()
                .get(Integer.class);
    }

    public void deleteTable(){
        String deleteTableURI = REST_URI + "/delete";
        client.target(deleteTableURI)
                .request()
                .delete();
    }
}
