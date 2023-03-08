package com.example.dto;

import com.example.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder //디자인 패턴중 하나. 빌더 패턴을 사용해 오브젝트를 생성할 수 있다.
@NoArgsConstructor //매개변수가 없는 생성자를 구현해준다.
@AllArgsConstructor //모든 멤버변수를 매개변수로 받는 생성자를 구현해준다.
@Data //게터, 세터를 구현해준다.
public class TodoDTO {
    private String id;
    private String title;
    private boolean done;

    public TodoDTO(final TodoEntity entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }
    public static TodoEntity toEntity(final TodoDTO dto){
        return TodoEntity.builder().id(dto.getId()).title(dto.getTitle()).done(dto.isDone()).build();
    }
}
