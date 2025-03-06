package com.daowen.entity;
import java.util.Date;

import javax.persistence.*;

/**
*  宿舍类型
*/
@Entity
public class   Sstype{
        
    
          //编码
          
              @Id
               private   int    id;
            
     
          //名称
             
               private   String    name;
          
     
          //居住人数
             
               private   int    renshu;
          
     
    
     public  int  getId(){
          return  id;
 }
public  void setId( int  id){
        this.id=id;
}

     public  String  getName(){
          return  name;
 }
public  void setName( String  name){
        this.name=name;
}

     public  int  getRenshu(){
          return  renshu;
 }
public  void setRenshu( int  renshu){
        this.renshu=renshu;
}

}