package tw.edu.ncu.im.Util;

import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.net.MalformedURLException;

import org.apache.commons.lang.Validate;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.core.CoreContainer;
/**
 * Searching the keyterm number appear in the index
 * using Wikipedia index and Solr server as the example 
 * @author Dean
 *
 */
public class IndexSearcher {
	// Singleton Design pattern only access it by getService() to ensure connection
	public static String SolrHomePath;
	public static String solrCoreName;
	public static final String DEFAULTHOMEPATH = "C:\\SOLRServer\\solr"; //DEFAULT solr place, not likely in this place
	public static final String DEFAULTCORENAME = "collection1";
	private static volatile SolrServer service; 
	
	public IndexSearcher(){
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
			synchronized(IndexSearcher.class){
				if(service==null){//Have to check again or have chance to fail
				service = initService();
				}
			}
		}
		return service;
	}
	
	/**
	 * searching the Server to get indexed size
	 * @param term searching term
	 * @return number of appear in indexed documents 
	 * @throws SolrServerException happen when server have connection problem
	 */
	public long searchTermSize(String term) throws SolrServerException{
		String queryTerm = "\"" + term + "\"";
		SolrQuery query = new SolrQuery();
		query.setQuery(term);
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
