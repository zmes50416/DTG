package tw.edu.ncu.im.Util;

import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.net.MalformedURLException;

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
	// Singleton Design pattern only access it by getServer() to ensure connection
	private static SolrServer service = getService(); 
	private static String SOLRHOMEPATH = "/Users/Dean/Documents/solr-4.9.0/example/solr";
	public IndexSearcher(){
	}
	/**
	 * @return the service
	 */
	public static SolrServer getService() {
		if(service == null){
			File home = new File(SOLRHOMEPATH);
			File f = new File(home, "solr.xml");
			CoreContainer container = new CoreContainer(SOLRHOMEPATH);
			container.load();
			System.out.println(container.getSolrHome());
			service  = new EmbeddedSolrServer(container, "collection1");
			
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
