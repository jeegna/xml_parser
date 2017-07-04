package com.esf.xmlParser.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.esf.xmlParser.entities.Asset;
import com.esf.xmlParser.entities.AssetClip;
import com.esf.xmlParser.entities.Audio;
import com.esf.xmlParser.entities.Effect;
import com.esf.xmlParser.entities.Format;
import com.esf.xmlParser.entities.Video;

/**
 * A Parser for fcpxml files
 * 
 * @author Jeegna Patel
 */
public class Parser {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	// Attributes for resources (asset, format, effect)
	private static final String ID = "id";
	private static final String UID = "uid";
	private static final String NAME = "name";

	// Common attributes
	private static final String SOURCE = "src";
	private static final String LANE = "lane";
	private static final String DURATION = "duration";
	private static final String OFFSET = "offset";
	private static final String REFERENCE = "ref";
	private static final String START = "start";
	private static final String ROLE = "role";

	// Attributes unique to asset tag
	private static final String ASSET = "asset";
	private static final String HAS_AUDIO = "hasAudio";
	private static final String HAS_VIDEO = "hasVideo";
	private static final String AUDIO_SOURCES = "audioSources";
	private static final String AUDIO_CHANNELS = "audioChannels";
	private static final String AUDIO_RATE = "audioRate";

	// Attributes unique to video tag
	private static final String VIDEO = "video";

	// Attributes unique to audio tag
	private static final String AUDIO = "audio";
	private static final String SRC_CH = "srcCh";
	private static final String SRC_ID = "srcID";

	// Attributes unique to asset-clip
	private static final String ASSET_CLIP = "asset-clip";
	private static final String AUDIO_ROLE = "audioRole";
	private static final String TC_FORMAT = "tcFormat";

	// Attributes unique to format
	private static final String FORMAT = "format";
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	private static final String FRAME_DURATION = "frameDuration";

	// Attributes unique to effect
	private static final String EFFECT = "effect";

	private Document doc;

	private List<Asset> assets;
	private List<Format> formats;
	private List<Effect> effects;

	/**
	 * Creates a Parser for the given file.
	 * 
	 * @param file
	 *            The absolute path of the file from which to get an XML
	 *            parsable Document.
	 * 
	 * @throws IOException
	 *             If any IO errors occur.
	 * @throws SAXException
	 *             If any parse errors occur.
	 * @throws ParserConfigurationException
	 *             If a DocumentBuilder cannot be created which satisfies the
	 *             configuration requested.
	 */
	public Parser(String file) throws ParserConfigurationException, SAXException, IOException {
		doc = getDocument(file);

		// Get main resources first so that they may be used to link their src
		// to other ref's.
		formats = getFormats();
		effects = getEffects();
		assets = getAssets();
	}

	/**
	 * Gets all information of &lt;asset&gt; tags from the document.
	 * 
	 * @return A List of Asset objects with the information from the document.
	 */
	public List<Asset> getAssets() {
		logger.info("Getting assets...");

		if (assets == null) {
			NodeList assets = doc.getElementsByTagName(ASSET);
			List<Asset> list = new ArrayList<Asset>();

			for (int i = 0; i < assets.getLength(); i++) {

				Node node = assets.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {

					Element element = (Element) node;

					Asset asset = new Asset();
					asset.setId(validateString(element.getAttribute(ID)));

					String hasAudio = element.getAttribute(HAS_AUDIO);
					asset.setHasAudio(hasAudio != null && hasAudio.equals("1"));

					String hasVideo = element.getAttribute(HAS_VIDEO);
					asset.setHasVideo(hasVideo != null && hasVideo.equals("1"));

					asset.setName(validateString(element.getAttribute(NAME)));
					asset.setUid(validateString(element.getAttribute(UID)));
					asset.setSrc(validateString(element.getAttribute(SOURCE)));
					asset.setAudioSources(validateNumber(element.getAttribute(AUDIO_SOURCES)));
					asset.setAudioChannels(validateNumber(element.getAttribute(AUDIO_CHANNELS)));
					asset.setAudioRate(validateNumber(element.getAttribute(AUDIO_RATE)));

					String duration = validateString(element.getAttribute(DURATION));
					String start = element.getAttribute(START);
					asset.setDuration(getTime(duration));
					asset.setStart(getTime(start));

					// Find corresponding format element.
					String formatId = validateString(element.getAttribute(FORMAT));
					Format format = null;
					for (Format f : formats) {
						if (f.getId().equals(formatId)) {
							format = f;
							break;
						}
					}
					if (format == null) {
						format = new Format();
					}
					asset.setFormat(format);

					list.add(asset);
				}
			}

			return list;
		} else {
			return assets;
		}
	}

	/**
	 * Gets all information of &lt;asset-clip&gt; tags from the document.
	 * 
	 * @return A List of AssetClip objects with the information from the
	 *         document.
	 */
	public List<AssetClip> getAssetClips() {
		logger.info("Getting asset clips...");

		NodeList assetClips = doc.getElementsByTagName(ASSET_CLIP);

		List<AssetClip> list = new ArrayList<AssetClip>();

		for (int i = 0; i < assetClips.getLength(); i++) {

			Node node = assetClips.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) node;

				AssetClip assetClip = new AssetClip();

				assetClip.setName(validateString(element.getAttribute(NAME)));
				assetClip.setLane(validateNumber(element.getAttribute(LANE)));
				assetClip.setRole(validateString(element.getAttribute(AUDIO_ROLE)));
				assetClip.setFormat(validateString(element.getAttribute(FORMAT)));
				assetClip.setTcFormat(validateString(element.getAttribute(TC_FORMAT)));

				String duration = validateString(element.getAttribute(DURATION));
				String offset = element.getAttribute(OFFSET);
				String start = element.getAttribute(START);
				assetClip.setDuration(getTime(duration));
				assetClip.setOffset(getTime(offset));
				assetClip.setStart(getTime(start));

				// Find corresponding asset element's sourcc file.
				String ref = validateString(element.getAttribute(REFERENCE));
				String src = null;
				for (Asset a : assets) {
					if (a.getId().equals(ref)) {
						src = a.getSrc();
						break;
					}
				}
				assetClip.setRef(src);

				list.add(assetClip);
			}
		}

		return list;
	}

	/**
	 * Gets an XML parsable Document from the given XML file name and path.
	 * 
	 * @param file
	 *            The absolute path of the file from which to get an XML
	 *            parsable Document.
	 * 
	 * @return An XML parsable Document from the given file name and path.
	 * 
	 * @throws IOException
	 *             If any IO errors occur.
	 * @throws SAXException
	 *             If any parse errors occur.
	 * @throws ParserConfigurationException
	 *             If a DocumentBuilder cannot be created which satisfies the
	 *             configuration requested.
	 */
	private Document getDocument(String file) throws ParserConfigurationException, SAXException, IOException {

		// Get file
		File fcpxml = new File(file);

		// Create document
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbFactory.newDocumentBuilder();
		Document doc = builder.parse(fcpxml);

		doc.getDocumentElement().normalize();

		return doc;
	}

	/**
	 * Gets all information of &lt;video&gt; tags from the document.
	 * 
	 * @return A List of Video objects with the information from the document.
	 */
	public List<Video> getVideos() {
		logger.info("Getting videos...");

		NodeList videos = doc.getElementsByTagName(VIDEO);
		List<Video> list = new ArrayList<Video>();

		for (int i = 0; i < videos.getLength(); i++) {

			Node node = videos.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) node;

				Video video = new Video();

				video.setName(validateString(element.getAttribute(NAME)));
				video.setLane(validateNumber(element.getAttribute(LANE)));
				video.setRef(validateString(element.getAttribute(REFERENCE)));

				String duration = validateString(element.getAttribute(DURATION));
				String start = validateString(element.getAttribute(START));
				String offset = validateString(element.getAttribute(OFFSET));
				video.setDuration(getTime(duration));
				video.setStart(getTime(start));
				video.setOffset(getTime(offset));

				list.add(video);
			}
		}

		return list;
	}

	/**
	 * Gets all information of &lt;audio&gt; tags from the document.
	 * 
	 * @return A List of Audio objects with the information from the document.
	 */
	public List<Audio> getAudios() {
		logger.info("Getting audios...");

		NodeList audios = doc.getElementsByTagName(AUDIO);
		List<Audio> list = new ArrayList<Audio>();

		for (int i = 0; i < audios.getLength(); i++) {

			Node node = audios.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {

				Element element = (Element) node;

				Audio audio = new Audio();

				audio.setRef(validateString(element.getAttribute(REFERENCE)));
				audio.setLane(validateNumber(element.getAttribute(LANE)));
				audio.setRole(validateString(element.getAttribute(ROLE)));
				audio.setOffset(validateString(element.getAttribute(OFFSET)));
				audio.setSrcCh(validateString(element.getAttribute(SRC_CH)));
				audio.setSrcID(validateNumber(element.getAttribute(SRC_ID)));

				String duration = validateString(element.getAttribute(DURATION));
				String start = validateString(element.getAttribute(START));
				String offset = validateString(element.getAttribute(OFFSET));
				audio.setDuration(getTime(duration));
				audio.setStart(getTime(start));
				audio.setOffset(getTime(offset));

				list.add(audio);
			}
		}

		return list;
	}

	/**
	 * Gets all information of &lt;audio&gt; tags from the document.
	 * 
	 * @return A List of Audio objects with the information from the document.
	 */
	public List<Format> getFormats() {
		logger.info("Getting formats...");

		if (formats == null) {
			NodeList formats = doc.getElementsByTagName(FORMAT);
			List<Format> list = new ArrayList<Format>();

			for (int i = 0; i < formats.getLength(); i++) {

				Node node = formats.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {

					Element element = (Element) node;

					Format format = new Format();

					format.setId(validateString(element.getAttribute(ID)));
					format.setName(validateString(element.getAttribute(NAME)));
					format.setWidth(validateNumber(element.getAttribute(WIDTH)));
					format.setHeight(validateNumber(element.getAttribute(HEIGHT)));

					String frameDuration = validateString(element.getAttribute(FRAME_DURATION));
					format.setFrameDuration(getFrameRate(frameDuration));

					list.add(format);
				}
			}

			return list;
		} else {
			return formats;
		}
	}

	/**
	 * Gets all information of &lt;audio&gt; tags from the document.
	 * 
	 * @return A List of Audio objects with the information from the document.
	 */
	public List<Effect> getEffects() {
		logger.info("Getting effects...");

		if (effects == null) {
			NodeList effects = doc.getElementsByTagName(EFFECT);
			List<Effect> list = new ArrayList<Effect>();

			for (int i = 0; i < effects.getLength(); i++) {

				Node node = effects.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {

					Element element = (Element) node;

					Effect effect = new Effect();

					effect.setId(validateString(element.getAttribute(ID)));
					effect.setName(validateString(element.getAttribute(NAME)));
					effect.setUid(validateString(element.getAttribute(UID)));
					effect.setSrc(validateString(element.getAttribute(SOURCE)));

					list.add(effect);
				}
			}

			return list;
		} else {
			return effects;
		}
	}

	private String getTime(String s) {
		// logger.info("Original time: " + s);
		String time = "0";

		if (s != null && !s.isEmpty()) {
			time = s;
			int division = s.indexOf('/');

			if (division != -1) {
				int length = s.length();

				String num = s.substring(0, division);
				String denom = s.substring(division + 1, length - 1);

				BigDecimal numerator = new BigDecimal(num);
				BigDecimal denominator = new BigDecimal(denom);

				time = numerator.divide(denominator, 2, RoundingMode.HALF_UP).toString();
			}
		}

		if (!time.endsWith("s")) {
			time += "s";
		}

		// logger.info("New time: " + time);
		return time;
	}

	private String getFrameRate(String s) {
		// logger.info("Original frame duration: " + s);
		String time = "0";

		if (s != null && !s.isEmpty()) {
			time = s;
			int division = s.indexOf('/');

			if (division != -1) {
				int length = s.length();

				String num = s.substring(0, division);
				String denom = s.substring(division + 1, length - 1);

				BigDecimal numerator = new BigDecimal(num);
				BigDecimal denominator = new BigDecimal(denom);

				time = denominator.divide(numerator, 2, RoundingMode.HALF_UP).toString();
			}
		}

		if (!time.endsWith("fps")) {
			time += " fps";
		}

		// logger.info("New frame rate: " + time);
		return time;
	}

	private int validateNumber(String n) {
		if (n == null || n.isEmpty()) {
			return -1;
		} else {
			return Integer.parseInt(n);
		}
	}

	private String validateString(String n) {
		if (n == null || n.isEmpty()) {
			return null;
		} else {
			return n;
		}
	}
}
