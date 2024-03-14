package com.example.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    public long id;
@NotEmpty
@Size(min = 2,message = "title should be more then two")
    private String title;
@NotEmpty
@Size(min = 2,message = "description should be more then two")
    private String description;
@NotEmpty
@Size(min = 2,message = "content should be more then two")
    private String content;

}
