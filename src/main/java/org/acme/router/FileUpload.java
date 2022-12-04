package org.acme.router;

import io.vertx.core.json.JsonObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.io.File;

public class FileUpload {


    String path = "C:\\Users\\tulga\\Desktop\\ChatbotRestAPi\\Chat-RestApi\\public\\Image";

//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public JsonObject upload(JsonObject body){
//        JsonObject jret = new JsonObject();
//
//          body.getString("message");
//
//        body.getString("seenDate");
//        body.getString("sentDate");
//        body.getString("from_user_id");
//        body.getString("to_user_id");
//
//
//       if(body.containsKey("content")){
//
//           JsonObject content = body.getJsonObject("content");
//
//           String type = body.getJsonObject("content").getString("type");
//           byte[] data = body.getJsonObject("content").getBinary("data");
//
//
//       }
//
//        return jret;
//    }

}
