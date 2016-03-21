package ch.erni.webapplication.service.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.jsoup.nodes.Document;
import org.junit.Test;

import ch.erni.webapplication.service.HtmlDocumentParser;

public class HtmlDocumentParserTest {
	
	@Resource(name = "scanUrl")
	public List<String> scanUrl;

	@Test
	public void functionalityNullTestGetADocumentOutOfAHtmlPage() throws IOException {
		HtmlDocumentParser htmlDocumentParser = new HtmlDocumentParser(null);
		List<String> scanUrl = new ArrayList<String>();
		scanUrl.add("https://sps2010.erninet.ch/news/Pages/NewsHome.aspx");
		List<Document> htmlDocument = htmlDocumentParser.getADocumentOutOfAHtmlPage(scanUrl);
		assertNotNull(htmlDocument);
	}

	@Test
	public void functionalityNullTestStringCheckForParsedDocument() throws IOException {
		HtmlDocumentParser htmlDocumentParser = new HtmlDocumentParser(null);
		List<String> scanUrl = new ArrayList<String>();
		scanUrl.add("https://sps2010.erninet.ch/news/Pages/NewsHome.aspx");
		List<String> searchWords = new ArrayList<String>();
		searchWords.add("Matthias Burkert");
		List<Document> htmlDocument = htmlDocumentParser.getADocumentOutOfAHtmlPage(scanUrl);
		Map<String, Boolean> mapWithUrlAndTheResultIfTheCheckedWordIsIncluded = htmlDocumentParser
				.stringCheckForParsedDocument(htmlDocument, searchWords);
		assertNotNull(mapWithUrlAndTheResultIfTheCheckedWordIsIncluded);
	}

	@Test
	public void functionalityControllFalseCaseTestStringCheckForParsedDocument() throws IOException {
		HtmlDocumentParser htmlDocumentParser = new HtmlDocumentParser(null);
		List<String> scanUrl = new ArrayList<String>();
		scanUrl.add("https://www.google.de/");
		List<String> searchWords = new ArrayList<String>();
		searchWords.add("Matthias Burkert");
		List<Document> htmlDocument = htmlDocumentParser.getADocumentOutOfAHtmlPage(scanUrl);
		Map<String, Boolean> mapWithUrlAndTheResultIfTheCheckedWordIsIncluded = htmlDocumentParser
				.stringCheckForParsedDocument(htmlDocument, searchWords);
		assertFalse(mapWithUrlAndTheResultIfTheCheckedWordIsIncluded.values().iterator().next());
	}
	
	@Test
	public void functionalityControllTrueCaseTestStringCheckForParsedDocument() throws IOException {
		HtmlDocumentParser htmlDocumentParser = new HtmlDocumentParser(null);
		List<String> scanUrl = new ArrayList<String>();
		scanUrl.add("https://www.google.de/");
		List<String> searchWords = new ArrayList<String>();
		searchWords.add("google");
		List<Document> htmlDocument = htmlDocumentParser.getADocumentOutOfAHtmlPage(scanUrl);
		Map<String, Boolean> mapWithUrlAndTheResultIfTheCheckedWordIsIncluded = htmlDocumentParser
				.stringCheckForParsedDocument(htmlDocument, searchWords);
		assertTrue(mapWithUrlAndTheResultIfTheCheckedWordIsIncluded.values().iterator().next());
	}
	
	@Test
	public void testDocumentBuilderForNullSafeBehavior(){
		HtmlDocumentParser htmlDocumentParser = new HtmlDocumentParser(null);
		assertNotNull(htmlDocumentParser.documentBuilder("https://www.google.de/"));
	}
	

}
