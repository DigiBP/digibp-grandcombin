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
	static String pubID="";
    static String title;
    static String pubType; 
    static String pubReview; 
    static String pubAudience; 
    static String pubAbstract;
    static String PubUri;
    static String pubDate;
    static String autor="";
    static int countAuthor=0;
	
     public void execute(DelegateExecution execution) throws JSONException{
         String I = (String)execution.getVariable("JsonInput");
         System.out.println("ch.fhnw.digibp.sevice2.execute()");
         System.out.println(I);
                  
         
       
        //String JSON_DATA="{\"metadata\":[{\"key\":\"dc.contributor.author\",\"value\":\"Witschel, Hans Friedrich\",\"identifier\":\"15384\"},{\"key\":\"dc.contributor.author\",\"value\":\"Hu, Bo\",\"identifier\":null},{\"key\":\"dc.contributor.author\",\"value\":\"Riss, Uwe\",\"identifier\":null},{\"key\":\"dc.contributor.author\",\"value\":\"Thönssen, Barbara\",\"identifier\":\"12016\"},{\"key\":\"dc.contributor.author\",\"value\":\"Brun, Roman\",\"identifier\":null},{\"key\":\"dc.contributor.author\",\"value\":\"Martin, Andreas\",\"identifier\":\"13087\"},{\"key\":\"dc.contributor.author\",\"value\":\"Hinkelmann, Knut\",\"identifier\":\"11943\"},{\"key\":\"dc.date.accessioned\",\"value\":\"2015-10-05T15:41:43Z\",\"identifier\":null},{\"key\":\"dc.date.available\",\"value\":\"2015-10-05T15:41:43Z\",\"identifier\":null},{\"key\":\"dc.date.issued\",\"value\":\"2010-01-01T00:00:00Z\",\"identifier\":null},{\"key\":\"dc.identifier.uri\",\"value\":\"http://hdl.handle.net/11654/9313\",\"identifier\":null},{\"key\":\"dc.description.abstract\",\"value\":\"We introduce a new approach supporting knowledge workers in sharing process-related knowledge. It is based on the insight that  while offering valuable context information  traditional business process modelling approaches are too rigid and inflexible to capture the actual way processes are executed. Therefore, business process models are made agile and open for changes during execution. To achieve this, the strict distinction between build time modelling and run time execution are softened and process activities are represented to the users in a way that allows for individual adaptations. That can be done by attaching resources, commenting on an issue or adding problems and solutions to an activity or process. In addition activities can be delegated or new (sub-)activities can be added. Thus, the model can adapt to the reality of actual process executions and valuable resources and experiences are proactively presented to users in the right context. A double-staged approach is chosen to apply the model in the real application scenario of an university.\",\"identifier\":null},{\"key\":\"dc.language.iso\",\"value\":\"en_UK\",\"identifier\":null},{\"key\":\"dc.accessRights\",\"value\":\"Anonymous\",\"identifier\":null},{\"key\":\"dc.subject.ddc\",\"value\":\"330 - Wirtschaft\",\"identifier\":null},{\"key\":\"dc.subject.ddc\",\"value\":\"005 - Computer Programmierung, Programme und Daten\",\"identifier\":null},{\"key\":\"dc.title\",\"value\":\"A Collaborative Approach to Maturing Process-related Knowledge\",\"identifier\":null},{\"key\":\"dc.type\",\"value\":\"04 - Beitrag Sammelband oder Konferenzschrift\",\"identifier\":null},{\"key\":\"dc.spatial\",\"value\":\"New York City\",\"identifier\":null},{\"key\":\"dc.event\",\"value\":\"8th International Conference on Business Process Management (BPM 2010)\",\"identifier\":null},{\"key\":\"dc.audience\",\"value\":\"Sonstige\",\"identifier\":null},{\"key\":\"fhnw.publicationState\",\"value\":\"Veröffentlicht\",\"identifier\":null},{\"key\":\"fhnw.ReviewType\",\"value\":\"Kein Peer Review\",\"identifier\":null},{\"key\":\"fhnw.InventedHere\",\"value\":\"unbekannt\",\"identifier\":null},{\"key\":\"OrgUnitID\",\"value\":\"FW726\",\"identifier\":null},{\"key\":\"OrgUnitName\",\"value\":\"Institut für Wirtschaftsinformatik\",\"identifier\":null},{\"key\":\"OrgUnitID\",\"value\":\"FW\",\"identifier\":null},{\"key\":\"OrgUnitName\",\"value\":\"Hochschule für Wirtschaft\",\"identifier\":null}]}";
	final JSONObject obj = new JSONObject(I);
                            
            final JSONArray data1 = obj.getJSONArray("metadata");
            final int n = data1.length();

            for (int i = 0; i < n; ++i) {
              final JSONObject publ = data1.getJSONObject(i);
              String Prov_S=publ.toString().replaceAll("[{]", "").replaceAll("[}]", "");

              String[] items = Prov_S.split(":");
            //  System.out.println(Arrays.toString(items));
              List<String> itemList = new ArrayList<String>(Arrays.asList(items));



                 // System.out.println(itemList.get(itemList.size()-1));
                  if(itemList.get(itemList.size()-1).equals("\"dc.contributor.author\"")){
                    if(countAuthor>0){
                        pubID+=",";
                        autor+=",";
                    }            
                   pubID+=itemList.get(1).replaceAll(",\"value\"","").replaceAll("\"", "");
                   autor+="\""+itemList.get(2).replaceAll(",\"key\"","").replaceAll("\"", "")+"\"";
                   countAuthor++;           
                }
                if(itemList.get(itemList.size()-1).equals("\"dc.date.issued\"")){            
                    pubDate=itemList.get(2).replaceAll("null","").replaceAll("\"", "");
                    pubDate="\""+pubDate.substring(0, pubDate.length()-3)+"\"";                    
                }
                 if(itemList.get(itemList.size()-1).equals("\"dc.description.abstract\"")){
                    pubAbstract=itemList.get(2).replaceAll("null","").replaceAll("\"", "");
                    pubAbstract="\""+pubAbstract.substring(0, pubAbstract.length()-4)+"\"";                    
                }
                 if(itemList.get(itemList.size()-1).equals("\"dc.identifier.uri\"")){
                     PubUri=itemList.get(2)+":"+itemList.get(3).replaceAll(",\"key\"","");          
                }
                 if(itemList.get(itemList.size()-1).equals("\"dc.title\"")){
                     title=itemList.get(2).replaceAll(",\"key\"","");
                }
                 if(itemList.get(itemList.size()-1).equals("\"dc.type\"")){
                    pubType=itemList.get(2).replaceAll(",\"key\"","");
                }
                if(itemList.get(itemList.size()-1).equals("\"dc.type\"")){
                    pubType=itemList.get(2).replaceAll(",\"key\"","");            
                }
                if(itemList.get(itemList.size()-1).equals("\"dc.audience\"")){            
                    pubAudience=itemList.get(2).replaceAll(",\"key\"","");
                }
                if(itemList.get(itemList.size()-1).equals("\"fhnw.ReviewType\"")){           
                    pubReview=itemList.get(2).replaceAll(",\"key\"","");
                }  

            }
                
            
               System.out.println("JSONTEST: "+execution.getVariable("JsonInput"));  
               System.out.println("Titel: "+title); 
               System.out.println("URI: "+PubUri);
               System.out.println("Abst: "+pubAbstract);
               System.out.println("pubType: "+pubType);
               System.out.println("pubAudience: "+pubAudience); 
               System.out.println("pubReview: "+pubReview);
               System.out.println("PubDate: "+pubDate);
               System.out.println(pubID);
               System.out.println(autor);

                   execution.setVariable("title", title);
                   execution.setVariable("autor", autor);
                   execution.setVariable("PubUri", PubUri);
                   execution.setVariable("pubAbstract", pubAbstract);
                   execution.setVariable("pubReview", pubReview);
                   execution.setVariable("pubDate", pubDate);
                   execution.setVariable("pubID", pubID);
            }

            public String executeResultVariable(String data){
                return "executeRedddddultVariable result: " + data;
            }
}


