package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentVO implements Serializable {
    private int id;
    private String name;
    private int age;
    private int cid;
    private MyClass myClass;
}
