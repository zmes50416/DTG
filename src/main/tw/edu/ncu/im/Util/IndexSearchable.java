package tw.edu.ncu.im.Util;

import org.apache.solr.client.solrj.SolrServerException;

public interface IndexSearchable {
	/**
	 * searching the Server to get how many document the term contain
	 * @param term searching term
	 * @return number of appear in indexed documents 
	 * @throws SolrServerException happen when server have connection problem
	 */
	public long searchTermSize(String term) throws SolrServerException;
	/**
	 * Search Multiple term's document
	 * @param terms
	 * @return
	 * @throws SolrServerException
	 */
	public long searchMultipleTerm(String[] terms) throws SolrServerException;
}
