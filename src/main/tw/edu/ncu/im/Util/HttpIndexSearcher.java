package tw.edu.ncu.im.Util;

import java.awt.datatransfer.StringSelection;
import java.net.MalformedURLException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
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
public class HttpIndexSearcher extends IndexSearchable{
	// Singleton Design pattern only access it by getServer() to ensure connection
	public static String url;

	/**
	 * @return the service
	 */
	protected SolrServer initService() {
		HttpSolrServer service;
		service = new HttpSolrServer(url);
		service.setAllowCompression(true);
		service.setDefaultMaxConnectionsPerHost(3000);
		service.setMaxTotalConnections(3000);
		service.setMaxRetries(1);
		return service;
	}
	

}
