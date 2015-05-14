package tw.edu.ncu.im.Util;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

public abstract class IndexSearchable {
	private static volatile SolrServer service; 

	/**
	 * 實踐不同方法的連接solrServer方式
	 * @return
	 */
	protected abstract SolrServer initService();
	
	public SolrServer getService(){
		if(service==null){//Only enter this once in a runtime
			synchronized(IndexSearchable.class){
				if(service==null){//Have to check again or have chance to fail
				service = initService();
				}
			}
		}
		return service;
	}
	/**
	 * searching the Server to get how many document the term contain
	 * @param term searching term
	 * @return number of appear in indexed documents 
	 * @throws SolrServerException happen when server have connection problem
	 */
	public long searchTermSize(String term) throws SolrServerException{
		String queryTerm = "\"" + term + "\"";
		SolrQuery query = new SolrQuery();
		query.setQuery(queryTerm);
		query.setHighlight(false);
		QueryResponse rsp = getService().query(query);
		SolrDocumentList docs = rsp.getResults();
		return docs.getNumFound();
	}
	/**
	 * Search Multiple term's document
	 * @param terms
	 * @return
	 * @throws SolrServerException
	 */
	public long searchMultipleTerm(String[] terms) throws SolrServerException{
		if(terms.length == 1){
			return searchTermSize(terms[0]);
		}
		
		StringBuilder builder = new StringBuilder();
		
		for(String term:terms){
				builder.append("+\"" +term+ "\"");
		}
		SolrQuery query = new SolrQuery();
		query.setQuery(builder.toString());
		query.setHighlight(false);
		QueryResponse rsp = getService().query(query);
		SolrDocumentList docs = rsp.getResults();
		return docs.getNumFound();
		
	}
	
	public void shutdown(){
		this.getService().shutdown();
	}
}
