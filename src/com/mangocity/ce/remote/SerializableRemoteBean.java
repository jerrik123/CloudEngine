package com.mangocity.ce.remote;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * 
* @ClassName: SerializableRemoteBean 
* @Description: TODO(序列化与反序列化) 
* @author Syungen
* @date 2015年8月25日 下午2:49:46 
*
 */
public class SerializableRemoteBean {
    public static String serializable(Object obj) throws IOException{
    	ByteArrayOutputStream bay =  new ByteArrayOutputStream();
    	ObjectOutputStream objOut =new  ObjectOutputStream(bay);
    	objOut.writeObject(obj);
    	return new BASE64Encoder().encode(bay.toByteArray());
    }
    
    public static Object unSerializable(String obj) throws IOException, ClassNotFoundException{
    	ByteArrayInputStream bay = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(obj));
    	ObjectInputStream in = new ObjectInputStream(bay);
    	return in.readObject();
    }
}
