package tw.edu.ncu.im.Util;

import java.net.MalformedURLException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
/**
 * Searching the keyterm number appear in the index
 * using Wikipedia index and Solr server as the example 
 * @author Dean
 *
 */
public class IndexSearcher {
	// Singleton Design pattern only access it by getServer() to ensure connection
	private static HttpSolrClient service=null; 
	protected String ip;
	IndexSearcher(String _ip){
		this.ip = _ip;
	}
	/**
	 * @return the service
	 */
	public static HttpSolrClient getService(String url) {
		if(service == null){
			service  = new HttpSolrClient(url);
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
		SolrQuery query = new SolrQuery();
		query.setQuery(term);
		QueryResponse rsp = getService(this.ip).query(query);
		SolrDocumentList docs = rsp.getResults();
		return docs.getNumFound();
	}
	
	

}
