package tw.edu.ncu.im.Util;

import java.awt.datatransfer.StringSelection;
import java.net.MalformedURLException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
/**
 * Searching the keyterm number appear in the index
 * using http post method to communicate with Solr server 
 * @author Dean
 *
 */
public class HttpIndexSearcher {
	// Singleton Design pattern only access it by getServer() to ensure connection
	private volatile static HttpSolrServer service; 
	public static String url;
	public HttpIndexSearcher(){
	}
	/**
	 * @return the service
	 */
	public static HttpSolrServer getService() {
		if(url==null){
			throw new IllegalStateException("please set the url before using it");
		}
		if(service == null){
			synchronized(HttpIndexSearcher.class){
				if(service==null){
				service  = new HttpSolrServer(url);
				}
			}
			
		}
		return service;
	}
	
	public long searchTermSize(String term) throws SolrServerException{
		String queryTerm = "\"" + term + "\"";
		SolrQuery query = new SolrQuery();
		query.setQuery(term);
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
