package core;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "song_rating")
@NamedQueries(
        value = {
                @NamedQuery(name = "SongRating.getAll", query = "SELECT a from song_rating a")
        }
)
public class SongRating implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // TODO: add logic

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
