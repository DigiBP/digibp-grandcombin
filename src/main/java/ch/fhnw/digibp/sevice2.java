/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fhnw.digibp;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import javax.inject.Named;
import org.json.*;
import java.util.*;



/**
 *
 * @author Bruno Rodrigues
 */
@Named
public class sevice2 implements JavaDelegate  {
    
//    
//    static String JsonInput = "{\"Data\":[{\"id\":12005,\"mail\":\"bruno.rodrigues@students.fhnw.chbruno.rodrigues@fhnw.ch\",\"name\":\"Rolf Dornberger\",\"metadata\":[{\"key\":\"dc.contributor.author\",\"value\":\"Menon, Dilip\",\"identifier\":\"71406\"},{\"key\":\"dc.contributor.author\",\"value\":\"Zwimpfer, Cédric\",\"identifier\":\"71421\"},{\"key\":\"dc.contributor.author\",\"value\":\"Hanne, Thomas\",\"identifier\":\"12123\"},{\"key\":\"dc.contributor.author\",\"value\":\"Dornberger, Rolf\",\"identifier\":\"12005\"},{\"key\":\"dc.date.accessioned\",\"value\":\"2015-09-21T09:30:58Z\",\"identifier\":null},{\"key\":\"dc.date.available\",\"value\":\"2015-09-21T09:30:58Z\",\"identifier\":null},{\"key\":\"dc.date.issued\",\"value\":\"2015-12-09\",\"identifier\":null},{\"key\":\"dc.identifier.uri\",\"value\":\"http://hdl.handle.net/11654/5067\",\"identifier\":null},{\"key\":\"dc.language.iso\",\"value\":\"en\",\"identifier\":null},{\"key\":\"dc.relation.ispartof\",\"value\":\"3rd International Symposium on Computational and Business Intelligence (ISCBI 2015)\",\"identifier\":null},{\"key\":\"dc.accessRights\",\"value\":\"Anonymous\",\"identifier\":null},{\"key\":\"dc.subject.ddc\",\"value\":\"330 - Wirtschaft\",\"identifier\":null},{\"key\":\"dc.title\",\"value\":\"Facility Layout Planning Using Fuzzy Closeness Computation and a Genetic Algorithm\",\"identifier\":null},{\"key\":\"dc.type\",\"value\":\"04 - Beitrag Sammelband oder Konferenzschrift\",\"identifier\":null},{\"key\":\"dc.spatial\",\"value\":\"Bali, Indonesia\",\"identifier\":null},{\"key\":\"dc.event.start\",\"value\":\"2015-12-07\",\"identifier\":null},{\"key\":\"dc.event.end\",\"value\":\"2015-12-08\",\"identifier\":null},{\"key\":\"dc.audience\",\"value\":\"Science\",\"identifier\":null},{\"key\":\"fhnw.publicationState\",\"value\":\"Published\",\"identifier\":null},{\"key\":\"fhnw.ReviewType\",\"value\":\"Anonymous ex ante peer review of a complete publication\",\"identifier\":null},{\"key\":\"fhnw.InventedHere\",\"value\":\"Yes\",\"identifier\":null},{\"key\":\"fhnw.PublishedSwitzerland\",\"value\":\"No\",\"identifier\":null},{\"key\":\"fhnw.IsStudentsWork\",\"value\":\"no\",\"identifier\":null},{\"key\":\"OrgUnitID\",\"value\":\"FW726\",\"identifier\":null},{\"key\":\"OrgUnitName\",\"value\":\"Institut für Wirtschaftsinformatik\",\"identifier\":null},{\"key\":\"OrgUnitID\",\"value\":\"FW\",\"identifier\":null},{\"key\":\"OrgUnitName\",\"value\":\"Hochschule für Wirtschaft\",\"identifier\":null}]}]}";
//    
    static String pubID="";
    static String title;
    static String pubType; 
    static String pubReview; 
    static String pubAudience; 
    static String pubAbstract;
    static String PubUri;
    static String pubDate;
    static String autor="";
    static String mainAutor;
    static String mainAutorID;
    static String mainAutorMail;
    static int countAuthor=0;
    static int countItem = 0;  
 
     public void execute(DelegateExecution execution) throws JSONException{
         String I = (String)execution.getVariable("metadata");
//         System.out.println("ch.fhnw.digibp.sevice2.execute()");
//         System.out.println(I);
           // String I = JsonInput;
            ArrayList pubList = new ArrayList();
            
            final JSONObject obj = new JSONObject(I);
            final JSONArray data1 = obj.getJSONArray("Data");
            final int n = data1.length();
            
            
            for (int i = 0; i < n; ++i) {
              final JSONObject publ = data1.getJSONObject(i);
              String Prov_S=publ.toString().replaceAll("\"identifier\":", "").replaceAll("\"value\":", "").replaceAll("\"key\":", "");
              String[] items = Prov_S.split("],");
            //  System.out.println(Arrays.toString(items));
              List<String> itemList = new ArrayList<String>(Arrays.asList(items));
                           
             
              for(String temp: itemList){
                  //System.out.println("Item Nr: "+c);
                //  System.out.println("Item Inhalt: "+temp);
              
                     temp = temp.replaceAll("\\{\"metadata\":\\[", "").replace("{", "");     
                    
                     String[] itemPub = temp.split("},");
                     
                    //System.out.println("Item:"+Arrays.toString(itemPub)+"SIZE: "+itemPub.length);
                    // System.out.println("UP"+itemPub[0]);
                     
                     for(String temp1:itemPub){
                          temp1 = temp1.replace("null", "Z\",");
                          String[] itemPubInfo = temp1.split("\",");
                          //System.out.println("Item:"+Arrays.toString(itemPubInfo));
                          
                          //System.out.println(itemPubInfo[(itemPubInfo.length)-1]);
                          //System.out.println("Index0: "+itemPubInfo[1]);
                          
                        if(itemPubInfo[(itemPubInfo.length)-1].equals("\"dc.contributor.author\"")){
                                                        
                            pubID+=itemPubInfo[0].replace("Z", "").replace("\"","");
                            autor+="\""+itemPubInfo[1].replace("\"", "")+"\",";
                            if(!itemPubInfo[0].equals("Z")){
                               pubID+=","; 
                            }
//                            System.out.println("PUBid: "+pubID);
//                            System.out.println("Author: "+autor);
                           
                        }
                         
                            if(itemPubInfo[(itemPubInfo.length)-1].equals("\"dc.date.issued\"")){

                               pubDate = itemPubInfo[1].replace(",\"", "");
                                 
                            }
                            if(itemPubInfo[(itemPubInfo.length)-1].equals("\"dc.description.abstract\"")){

                               pubAbstract = itemPubInfo[1].replace(",\"", "");
                                 
                            }
                            if(itemPubInfo[(itemPubInfo.length)-1].equals("\"dc.identifier.uri\"")){

                               PubUri = itemPubInfo[1].replace(",\"", "");
                                 
                            }
                            if(itemPubInfo[(itemPubInfo.length)-1].equals("\"dc.title\"")){

                               title = itemPubInfo[1].replace(",\"", "");
                               
                            }
                            if(itemPubInfo[(itemPubInfo.length)-1].equals("\"dc.type\"")){

                               pubType = itemPubInfo[1].replace(",\"", "");
                               
                            }
                            if(itemPubInfo[(itemPubInfo.length)-1].equals("\"dc.audience\"")){

                               pubAudience = itemPubInfo[1].replace(",\"", "");
                               
                            }
                            if(itemPubInfo[(itemPubInfo.length)-1].equals("\"fhnw.ReviewType\"")){

                               pubReview = itemPubInfo[1].replace(",\"", "");
                               
                            }
                            if(itemPub.length==1){
                            mainAutorMail = itemPubInfo[0].replace("\"mail\":", "").replace("\"", "");
                            mainAutor = itemPubInfo[1].replace("\"name\":", "").replace("\"", "");
                           
                            mainAutorID = itemPubInfo[2].replace("\"id\":", "").replace("\"", "").replace("}", "");
                           
                             
                            System.out.println("mainAutor: "+mainAutor); 
                            System.out.println("mainAutorMail: "+mainAutorMail);
                            System.out.println("mainAutorID: "+mainAutorID);
                            System.out.println("Abstract: "+pubAbstract);
                            System.out.println("URI: "+PubUri);
                            System.out.println("Title: "+title); 
                            System.out.println("pubType: "+pubType); 
                            System.out.println("pubAudience: "+pubAudience);
                            System.out.println("pubReview: "+pubReview);                             
                            System.out.println("Pubdate: "+pubDate);
                            
                            HashMap pub= new HashMap();
                            pub.put("PubUri",PubUri);
                            pub.put("title",title);

                            pub.put("mainAutor",mainAutor);
                            pub.put("mainAutorMail",mainAutorMail);
                            pub.put("mainAutorID",mainAutorID);
                            
                            pub.put("pubType",pubType);
                            pub.put("pubType",pubAudience);
                            pub.put("pubReview",pubReview);
                            pub.put("Pubdate",pubDate);
                            pub.put("pubAbstract",pubAbstract);
                            pub.put("publication", "ja");
                            pubList.add(pub);
                            
                            System.out.println("--------------ENDE!!!-"+"COUNT:"+pubList.size());
                            
                             System.out.println(pubList.get(pubList.size()-1));
                         }
                     }
                  
                  
                  
              }
            }
            

             System.out.println("Titel: "+title); 
               System.out.println("URI: "+PubUri);
               System.out.println("Abst: "+pubAbstract);
               System.out.println("pubType: "+pubType);
               System.out.println("pubAudience: "+pubAudience); 
               System.out.println("pubReview: "+pubReview);
               System.out.println("PubDate: "+pubDate);
               System.out.println(pubID);
               System.out.println(autor);
               System.out.println("VERSION: LAURA IST DA!");
               
               ArrayList uberPub = new ArrayList();
                    for(Object A:pubList){
                       uberPub.add(A);
                    }
                    for(Object B:uberPub){
                        System.out.println(B);
                    }
                    
                    
                   execution.setVariable("Title", title);
                   execution.setVariable("PubAuthors", autor);
                   execution.setVariable("publikationid", PubUri);
                   execution.setVariable("PubAbstract", pubAbstract);
                   execution.setVariable("PubPeerReview", pubReview);
                   execution.setVariable("PubType", pubType);
                   execution.setVariable("PubAudience", pubAudience);
                   execution.setVariable("datum", pubDate);
                   execution.setVariable("PubID", pubID);
                   execution.setVariable("publication", "ja");
                   execution.setVariable("AuthorMain",mainAutor);
                   execution.setVariable("AuthorMainMail",mainAutorMail);
                   execution.setVariable("AuthorMainID",mainAutorID);
                   execution.setVariable("PubAuthorsIDs", pubID);         
                   execution.setVariable("PubListSize", pubList.size());
                   execution.setVariable("pubList", pubList);
                   
                   pubList.clear();
                   pubID="";
                   pubDate="";
                   pubAudience=""; 
                   pubType="";  
                   pubReview="";
                   pubAbstract="";
                   PubUri="";
                   autor="";
                   title="";
                           
                           
            }

            public String executeResultVariable(String data){
                return "executeRedddddultVariable result: " + data;
            }
}


