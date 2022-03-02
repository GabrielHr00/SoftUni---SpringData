package _04;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "visitations")
public class Visitation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDateTime date;

    private String comments;

}
