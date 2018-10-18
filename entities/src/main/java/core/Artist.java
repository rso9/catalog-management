package core;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "artist")
@NamedQueries(
        value = {
                @NamedQuery(name = "Artist.getAll", query = "SELECT a from artist a")
        }
)
public class Artist implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String artistName;
    // TODO: more specific fields (once determined)
    // ...

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
}
