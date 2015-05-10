package tw.edu.ncu.im.Util;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.core.CoreContainer;
/**
 * using Embedded way to communicate with Solr server
 * 
 * @author Dean
 *
 */
public class EmbeddedIndexSearcher implements IndexSearchable {
	// Singleton Design pattern only access it by getService() to ensure connection
	public static String SolrHomePath;
	public static String solrCoreName;
	public static final String DEFAULTHOMEPATH = "C:\\SOLRServer\\solr"; //DEFAULT solr place, not likely in this place
	public static final String DEFAULTCORENAME = "collection1";
	private static volatile SolrServer service; 
	
	public EmbeddedIndexSearcher(){
	}
	
	/**
	 * Initialize Server setup
	 * @return server 
	 */
	private static SolrServer initService(){
		if(SolrHomePath==null){
			throw new IllegalStateException("Please set the home path of Solr Location");
		}
		if(solrCoreName==null){
			throw new IllegalStateException("Please set the solr core name");
		}
		CoreContainer container = new CoreContainer(SolrHomePath);
		container.load();
		System.out.println(container.getCoreNames());
		EmbeddedSolrServer embeddedService  = new EmbeddedSolrServer(container, solrCoreName);
		System.out.println("Solr System conntected!");
		return embeddedService;
	}
	/**
	 * @return the service
	 */
	public static SolrServer getService() {
		if(service==null){//Only enter this once in a runtime
			synchronized(EmbeddedIndexSearcher.class){
				if(service==null){//Have to check again or have chance to fail
				service = initService();
				}
			}
		}
		return service;
	}
	

	public long searchTermSize(String term) throws SolrServerException{
		String queryTerm = "\"" + term + "\"";
		SolrQuery query = new SolrQuery();
		query.setQuery(queryTerm);
		query.setHighlight(false);
		QueryResponse rsp = getService().query(query);
		SolrDocumentList docs = rsp.getResults();
		return docs.getNumFound();
	}

	public long searchMultipleTerm(String[] terms) throws SolrServerException{
		if(terms.length == 1){
			return searchTermSize(terms[0]);
		}
		
		StringBuilder builder = new StringBuilder();
		
		for(String term:terms){
				builder.append("+\"" +term+ "\"");
		}
		return searchTermSize(builder.toString());
		
	}
	
	

}
