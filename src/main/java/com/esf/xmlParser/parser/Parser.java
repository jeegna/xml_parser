package com.esf.xmlParser.parser;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
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
import com.esf.xmlParser.entities.Clip;
import com.esf.xmlParser.entities.Effect;
import com.esf.xmlParser.entities.Format;
import com.esf.xmlParser.entities.Video;

/**
 * A Parser for FCPSML files.
 * 
 * @author Jeegna Patel
 * @version
 * @since 1.8
 */
public class Parser {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	// Attributes for resources (asset, format, effect).
	private static final String ID = "id";
	private static final String UID = "uid";
	private static final String NAME = "name";

	// Common attributes.
	private static final String SOURCE = "src";
	private static final String LANE = "lane";
	private static final String DURATION = "duration";
	private static final String OFFSET = "offset";
	private static final String REFERENCE = "ref";
	private static final String START = "start";
	private static final String ROLE = "role";

	// Attributes unique to asset tag.
	private static final String ASSET = "asset";
	private static final String HAS_AUDIO = "hasAudio";
	private static final String HAS_VIDEO = "hasVideo";
	private static final String AUDIO_SOURCES = "audioSources";
	private static final String AUDIO_CHANNELS = "audioChannels";
	private static final String AUDIO_RATE = "audioRate";

	// Attributes unique to video tag.
	private static final String VIDEO = "video";

	// Attributes unique to audio tag.
	private static final String AUDIO = "audio";
	private static final String SRC_CH = "srcCh";
	private static final String SRC_ID = "srcID";

	// Attributes unique to asset clip tag.
	private static final String ASSET_CLIP = "asset-clip";
	private static final String AUDIO_ROLE = "audioRole";
	private static final String TC_FORMAT = "tcFormat";

	// Attributes unique to format tag.
	private static final String FORMAT = "format";
	private static final String WIDTH = "width";
	private static final String HEIGHT = "height";
	private static final String FRAME_DURATION = "frameDuration";

	// Attributes unique to effect tag.
	private static final String EFFECT = "effect";

	// Attributes unique to clip tag.
	private static final String CLIP = "clip";

	private Document doc;

	/**
	 * Creates a Parser for the given file.
	 * 
	 * @param file
	 *            The absolute path of the file from which to get an XML
	 *            Document.
	 * 
	 * @throws IOException
	 *             If any IO errors occur.
	 * @throws SAXException
	 *             If any parse errors occur.
	 * @throws ParserConfigurationException
	 *             If a DocumentBuilder cannot be created which satisfies the
	 *             configuration requested.
	 * @throws SQLException
	 *             If an SQLException occurs.
	 * @throws ClassNotFoundException
	 *             If the JDBC driver is not found.
	 */
	public Parser(String file)
			throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, SQLException {
		doc = getDocument(file);
	}

	/**
	 * Gets an XML Document from the given XML file name and path.
	 * 
	 * @param file
	 *            The absolute path of the file from which to get an XML
	 *            Document.
	 * 
	 * @return An XML Document from the given file name and path.
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
	 * Gets all information of &lt;asset&gt; tags from the document.
	 * 
	 * @return A List of Asset objects with the information from the document.
	 */
	public List<Asset> getAssets() {
		logger.info("Getting assets...");

		NodeList assets = doc.getElementsByTagName(ASSET);
		List<Asset> list = new ArrayList<Asset>();

		for (int i = 0; i < assets.getLength(); i++) {

			Node node = assets.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				list.add(createAsset(node));
			}
		}

		return list;
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
				list.add(createAssetClip(node));
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
				list.add(createAudio(node));
			}
		}

		return list;
	}

	/**
	 * Gets all information of &lt;clip&gt; tags from the document.
	 * 
	 * @return A List of Clip objects with the information from the document.
	 */
	public List<Clip> getClips() {
		logger.info("Getting clips...");

		NodeList clips = doc.getElementsByTagName(CLIP);
		List<Clip> list = new ArrayList<Clip>();

		for (int i = 0; i < clips.getLength(); i++) {

			Node node = clips.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				list.add(createClip(node));
			}
		}

		return list;
	}

	/**
	 * Gets all information of &lt;effect&gt; tags from the document.
	 * 
	 * @return A List of Effect objects with the information from the document.
	 */
	public List<Effect> getEffects() {
		logger.info("Getting effects...");

		NodeList effects = doc.getElementsByTagName(EFFECT);
		List<Effect> list = new ArrayList<Effect>();

		for (int i = 0; i < effects.getLength(); i++) {

			Node node = effects.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				list.add(createEffect(node));
			}
		}

		return list;
	}

	/**
	 * Gets all information of &lt;format&gt; tags from the document.
	 * 
	 * @return A List of Format objects with the information from the document.
	 */
	public List<Format> getFormats() {
		logger.info("Getting formats...");

		NodeList formats = doc.getElementsByTagName(FORMAT);
		List<Format> list = new ArrayList<Format>();

		for (int i = 0; i < formats.getLength(); i++) {

			Node node = formats.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				list.add(createFormat(node));
			}
		}

		return list;
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
				list.add(createVideo(node));
			}
		}

		return list;
	}

	/**
	 * Creates an Asset entity from the given node from the XML file.
	 * 
	 * @param node
	 *            The Node which holds the Asset's information.
	 * @return The Asset with it's corresponding information. Please note any
	 *         linked entities, such as Format, Asset, or Effect are not
	 *         complete entities. The field only contains the linked entity's id
	 *         number.
	 */
	private Asset createAsset(Node node) {
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
		Format format = new Format();
		format.setId(formatId);
		asset.setFormat(format);

		return asset;
	}

	/**
	 * Creates an AssetClip entity from the given node from the XML file.
	 * 
	 * @param node
	 *            The Node which holds the AssetClip's information.
	 * @return The AssetClip with it's corresponding information. Please note
	 *         any linked entities, such as Format, Asset, or Effect are not
	 *         complete entities. The field only contains the linked entity's id
	 *         number.
	 */
	private AssetClip createAssetClip(Node node) {
		Element element = (Element) node;

		AssetClip assetClip = new AssetClip();

		assetClip.setName(validateString(element.getAttribute(NAME)));
		assetClip.setLane(validateNumber(element.getAttribute(LANE)));
		assetClip.setRole(validateString(element.getAttribute(AUDIO_ROLE)));
		assetClip.setTcFormat(validateString(element.getAttribute(TC_FORMAT)));

		String duration = validateString(element.getAttribute(DURATION));
		String offset = element.getAttribute(OFFSET);
		String start = element.getAttribute(START);
		assetClip.setDuration(getTime(duration));
		assetClip.setOffset(getTime(offset));
		assetClip.setStart(getTime(start));

		// Find corresponding asset element's source file.
		String ref = validateString(element.getAttribute(REFERENCE));
		Asset asset = new Asset();
		asset.setId(ref);
		assetClip.setAsset(asset);

		// Find corresponding format element.
		String formatId = validateString(element.getAttribute(FORMAT));
		Format format = new Format();
		format.setId(formatId);
		assetClip.setFormat(format);

		return assetClip;
	}

	/**
	 * Creates an Audio entity from the given node from the XML file.
	 * 
	 * @param node
	 *            The Node which holds the Audio information.
	 * @return The Audio with it's corresponding information. Please note any
	 *         linked entities, such as Format, Asset, or Effect are not
	 *         complete entities. The field only contains the linked entity's id
	 *         number.
	 */
	private Audio createAudio(Node node) {
		Element element = (Element) node;

		Audio audio = new Audio();

		audio.setLane(validateNumber(element.getAttribute(LANE)));
		audio.setRole(validateString(element.getAttribute(ROLE)));
		audio.setOffset(validateString(element.getAttribute(OFFSET)));
		audio.setSrcCh(validateString(element.getAttribute(SRC_CH)));
		audio.setSrcId(validateNumber(element.getAttribute(SRC_ID)));

		String duration = validateString(element.getAttribute(DURATION));
		String start = validateString(element.getAttribute(START));
		String offset = validateString(element.getAttribute(OFFSET));
		audio.setDuration(getTime(duration));
		audio.setStart(getTime(start));
		audio.setOffset(getTime(offset));

		// Find corresponding asset element's source file.
		String ref = validateString(element.getAttribute(REFERENCE));
		Asset asset = new Asset();
		asset.setId(ref);
		audio.setAsset(asset);

		// Find parent Clip node.
		Node parent = node;
		boolean foundClip = false;
		do {
			parent = parent.getParentNode();
			if (parent == null) {
				break;
			}
			String name = parent.getNodeName();
			if (name == CLIP) {
				foundClip = true;
			}
		} while (!foundClip);
		if (foundClip) {
			Clip clip = createClip(parent);
			audio.setName(clip.getName());
			audio.setTcFormat(clip.getTcFormat());
		}

		return audio;
	}

	/**
	 * Creates a Clip entity from the given node from the XML file.
	 * 
	 * @param node
	 *            The Node which holds the Clip's information.
	 * @return The Clip with it's corresponding information. Please note any
	 *         linked entities, such as Format, Asset, or Effect are not
	 *         complete entities. The field only contains the linked entity's id
	 *         number.
	 */
	private Clip createClip(Node node) {
		Element element = (Element) node;

		Clip clip = new Clip();

		clip.setName(validateString(element.getAttribute(NAME)));
		clip.setTcFormat(validateString(element.getAttribute(TC_FORMAT)));

		String duration = validateString(element.getAttribute(DURATION));
		String start = validateString(element.getAttribute(START));
		String offset = validateString(element.getAttribute(OFFSET));
		clip.setDuration(getTime(duration));
		clip.setStart(getTime(start));
		clip.setOffset(getTime(offset));

		return clip;
	}

	/**
	 * Creates an Effect entity from the given node from the XML file.
	 * 
	 * @param node
	 *            The Node which holds the Effect information.
	 * @return The Effect with it's corresponding information. Please note any
	 *         linked entities, such as Format, Asset, or Effect are not
	 *         complete entities. The field only contains the linked entity's id
	 *         number.
	 */
	private Effect createEffect(Node node) {
		Element element = (Element) node;

		Effect effect = new Effect();

		effect.setId(validateString(element.getAttribute(ID)));
		effect.setName(validateString(element.getAttribute(NAME)));
		effect.setUid(validateString(element.getAttribute(UID)));
		effect.setSrc(validateString(element.getAttribute(SOURCE)));

		return effect;
	}

	/**
	 * Creates a Format entity from the given node from the XML file.
	 * 
	 * @param node
	 *            The Node which holds the Format's information.
	 * @return The Format with it's corresponding information. Please note any
	 *         linked entities, such as Format, Asset, or Effect are not
	 *         complete entities. The field only contains the linked entity's id
	 *         number.
	 */
	private Format createFormat(Node node) {
		Element element = (Element) node;

		Format format = new Format();

		format.setId(validateString(element.getAttribute(ID)));
		format.setName(validateString(element.getAttribute(NAME)));
		format.setWidth(validateNumber(element.getAttribute(WIDTH)));
		format.setHeight(validateNumber(element.getAttribute(HEIGHT)));

		String frameDuration = validateString(element.getAttribute(FRAME_DURATION));
		format.setFrameDuration(getFrameRate(frameDuration));

		return format;
	}

	/**
	 * Creates a Video entity from the given node from the XML file.
	 * 
	 * @param node
	 *            The Node which holds the Video's information.
	 * @return The Video with it's corresponding information. Please note any
	 *         linked entities, such as Format, Asset, or Effect are not
	 *         complete entities. The field only contains the linked entity's id
	 *         number.
	 */
	private Video createVideo(Node node) {
		Element element = (Element) node;

		Video video = new Video();

		video.setName(validateString(element.getAttribute(NAME)));
		video.setLane(validateNumber(element.getAttribute(LANE)));

		String duration = validateString(element.getAttribute(DURATION));
		String start = validateString(element.getAttribute(START));
		String offset = validateString(element.getAttribute(OFFSET));
		video.setDuration(getTime(duration));
		video.setStart(getTime(start));
		video.setOffset(getTime(offset));

		// Find corresponding asset element.
		String ref = validateString(element.getAttribute(REFERENCE));
		Asset asset = new Asset();
		asset.setId(ref);
		video.setAsset(asset);

		// Find parent Clip node.
		Node parent = node;
		boolean foundClip = false;
		do {
			parent = parent.getParentNode();

			/*
			 * The only way parent is null is if the node whose parent was
			 * attempted to be retrieved does not have a parent. In which case,
			 * the Video tag should have all of its information.
			 */
			if (parent == null) {
				break;
			}

			String name = parent.getNodeName();
			if (name == CLIP) {
				foundClip = true;
			}
		} while (!foundClip);

		if (foundClip) {
			Clip clip = createClip(parent);
			video.setName(clip.getName());
			video.setTcFormat(clip.getTcFormat());
		}

		return video;
	}

	/**
	 * Parses the given string to a format suitable to represent time stamps.
	 * The time stamp will be represented as: HH:MM:SS
	 * 
	 * @param s
	 *            The string to convert to a time stamp.
	 * @return The time stamp.
	 */
	private String convertToTimeStamp(BigDecimal num) {
		String timeStamp = "";

		int numberOfHours = (num.intValue() % 86400) / 3600;
		int numberOfMinutes = ((num.intValue() % 86400) % 3600) / 60;
		int numberOfSeconds = ((num.intValue() % 86400) % 3600) % 60;

		if (numberOfHours < 10) {
			timeStamp += "0" + numberOfHours + ":";
		} else {
			timeStamp += numberOfHours + ":";
		}

		if (numberOfMinutes < 10) {
			timeStamp += "0" + numberOfMinutes + ":";
		} else {
			timeStamp += numberOfMinutes + ":";
		}

		if (numberOfSeconds < 10) {
			timeStamp += "0" + numberOfSeconds;
		} else {
			timeStamp += numberOfSeconds;
		}

		return timeStamp;
	}

	/**
	 * Parses the given string to a frame rate.
	 * 
	 * @param s
	 *            The string to convert to a frame rate.
	 * @return The frame rate.
	 */
	private String getFrameRate(String s) {
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

		return time;
	}

	/**
	 * Parses the given string to milliseconds.
	 * 
	 * @param s
	 *            The string to convert to milliseconds.
	 * @return The time in milliseconds.
	 */
	private String getTime(String s) {
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

				BigDecimal quotient = numerator.divide(denominator, 2, RoundingMode.HALF_UP);

				time = convertToTimeStamp(quotient);
			}
		}

		return time;
	}

	/**
	 * Validates whether or not the given string is a valid number.
	 * 
	 * @param n
	 *            The string to validate.
	 * @return An integer representation of the given string, or -1 if the
	 *         string is null or empty.
	 * 
	 * @throws NumberFormatException
	 *             if the given string cannot be converted to an integer.
	 */
	private int validateNumber(String n) {
		if (n == null || n.isEmpty()) {
			return -1;
		} else {
			return Integer.parseInt(n);
		}
	}

	/**
	 * Validates whether or not the given string is a valid. A valid string is
	 * one that is not empty.
	 * 
	 * @param n
	 *            The string to validate.
	 * @return The given string itself or null if the string is empty.
	 */
	private String validateString(String n) {
		if (n == null || n.isEmpty()) {
			return null;
		} else {
			return n;
		}
	}
}
