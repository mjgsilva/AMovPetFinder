package pt.isec.amov.petfinder.entities;

import pt.isec.amov.petfinder.Validation;
import pt.isec.amov.petfinder.core.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

/**
 *
 */
public class Post {
    private int postId;
    private int userId;
    private PostType type;
    private Metadata metadata;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public PostType getType() {
        return type;
    }

    public void setType(PostType type) {
        this.type = type;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public static class Metadata {
        private Specie specie;
        private AnimalSize size;
        private final EnumSet<AnimalColor> colors = EnumSet.noneOf(AnimalColor.class);
        private List<byte[]> images = new ArrayList<byte[]>();
        private Location location;
        private Date publicationDate;

        public Specie getSpecie() {
            return specie;
        }

        public void setSpecie(final Specie specie) {
            this.specie = specie;
        }

        public AnimalSize getSize() {
            return size;
        }

        public void setSize(final AnimalSize size) {
            Validation.assertNotNull(size, "The size cannot be null");
            this.size = size;
        }

        public EnumSet<AnimalColor> getColors() {
            return colors;
        }

        public List<byte[]> getImages() {
            return images;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(final Location location) {
            this.location = location;
        }

        public Date getPublicationDate() {
            return publicationDate;
        }

        public void setPublicationDate(final Date publicationDate) {
            this.publicationDate = publicationDate;
        }
    }

}
