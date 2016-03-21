package ch.erni.webapplication.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HtmlDocumentParser {

	org.slf4j.Logger logger;

	@Resource(name = "scanUrl")
	public List<String> scanUrl;

	@Resource(name = "searchWords")
	public List<String> searchWords;

	@Resource(name = "crawlingDeepness")
	public int crawlingDeepness;

	private HtmlPageUrlCrawler htmlPageUrlCrawler;

	@Autowired
	public HtmlDocumentParser(HtmlPageUrlCrawler htmlPageUrlCrawler) {
		this.htmlPageUrlCrawler = htmlPageUrlCrawler;
	}

	public List<Document> getAllDocuments(List<Document> parsedHtmlDocument) {
		Map<String, List<String>> mapWithListsFromAllRuns = new HashMap<String, List<String>>();
		List<String> listWithAllUrlsFromOneLoopRun;
		for (Document document : parsedHtmlDocument) {
			if (document != null) {
				listWithAllUrlsFromOneLoopRun = htmlPageUrlCrawler.getListWithUrlsFromWebPage(document);
				mapWithListsFromAllRuns.put(document.id(), listWithAllUrlsFromOneLoopRun);
			}
		}
		if (parsedHtmlDocument.size() != 0) {
			getAllDocuments(documentListBuilder(mapWithListsFromAllRuns));
		}
		return documentListBuilder(mapWithListsFromAllRuns);
	}

	public Map<String, Boolean> stringCheckForParsedDocument(List<Document> parsedHtmlDocument,
			List<String> textToSearch) {
		Map<String, Boolean> mapWithBaseUrlAndInformationIfCheckedStringIsIncluded = new HashMap<String, Boolean>();
		boolean hasTheSearchedWord = false;
		for (String wordWichShouldSearchedInTheHtmlText : textToSearch) {
			if (wordWichShouldSearchedInTheHtmlText != null) {
				for (Document currentDocumentFromList : parsedHtmlDocument) {
					if (currentDocumentFromList != null) {
						hasTheSearchedWord = currentDocumentFromList.toString()
								.contains(wordWichShouldSearchedInTheHtmlText);
						mapWithBaseUrlAndInformationIfCheckedStringIsIncluded.put(currentDocumentFromList.baseUri(),
								hasTheSearchedWord);
					}
				}
			}
		}
		return mapWithBaseUrlAndInformationIfCheckedStringIsIncluded;
	}

	public List<Document> getADocumentOutOfAHtmlPage(List<String> scanUrl) {
		List<Document> listWithHtmlDocuments = new ArrayList<Document>();
		for (String urlToScan : scanUrl) {
			if (scanUrl != null) {
				listWithHtmlDocuments.add(documentBuilder(urlToScan));
			}
		}
		return listWithHtmlDocuments;
	}

	public Document documentBuilder(String documentBaseUrl) {
		Document doc = null;
		try {
			URL urlForConnectionCheck = new URL(documentBaseUrl);
			HttpURLConnection connection = (HttpURLConnection) urlForConnectionCheck.openConnection();
			if (connection != null && connection.getResponseCode() == 200) {
				doc = Jsoup.connect(documentBaseUrl).get();
			}
		} catch (IOException e) {
			logger.error(e.toString());
		}
		return doc;
	}

	public List<Document> documentListBuilder(Map<String, List<String>> listWithBaseUrlAndAllContainingUrl) {
		List<Document> crawledUrlsAsDocuments = new ArrayList<Document>();
		for (String keyforTheMap : listWithBaseUrlAndAllContainingUrl.keySet()) {
			List<String> listWithUrls = listWithBaseUrlAndAllContainingUrl.get(keyforTheMap);
			for (String pageUrl : listWithUrls) {
				UrlValidator urlValidator = new UrlValidator();
				if (urlValidator.isValid(pageUrl)) {
					crawledUrlsAsDocuments.add(documentBuilder(pageUrl));
				}
			}
		}
		return crawledUrlsAsDocuments;
	}
}
