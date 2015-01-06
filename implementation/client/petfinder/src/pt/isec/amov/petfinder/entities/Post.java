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
    private PostType type;
    private List<byte[]> images = new ArrayList<byte[]>();
    private Location location;
    private Metadata metadata = new Metadata();
    private Date publicationDate;

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public PostType getType() {
        return type;
    }

    public void setType(PostType type) {
        this.type = type;
    }

    public void setImages(byte[] image) {
        images.add(image);
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

    public Metadata getMetadata() {
        return metadata;
    }

    public static class Metadata {
        private AnimalSpecie specie;
        private AnimalSize size;
        private final EnumSet<AnimalColor> colors = EnumSet.noneOf(AnimalColor.class);

        public AnimalSpecie getSpecie() {
            return specie;
        }

        public void setSpecie(final AnimalSpecie specie) {
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
    }

}
