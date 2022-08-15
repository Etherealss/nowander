package test;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wtk
 * @since 2022-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("like_record_test2")
public class LikeRecordTest2 implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;

    private Integer targetId;

    private Integer targetType;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
