package pt.isec.amov.petfinder.rest;

/**
 * Created by mgois on 03-01-2015.
 */
public interface PostConstants {

    public static final String POST_ID = "postId";
    public static final String USER_ID = "userId";
    public static final String TYPE = "type";

    public static final String METADATA = "metadata";

    public interface Metadata {
        public static final String SPECIE = "specie";
        public static final String SIZE = "size";
        public static final String COLOR = "color";
        public static final String IMAGES = "images";
        public static final String LOCATION = "location";
        public static final String PUBDATE = "publicationDate";
    }

}
