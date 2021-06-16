package top.lijingyuan.scheduler.elastic.job;

import lombok.Data;

/**
 * FileCustom
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-16
 * @since 1.0.0
 */
@Data
public class FileCustom {

    private String id;

    private String name;

    private String type;

    private String content;

    private boolean backedUp = false;

    public FileCustom(String id, String name, String type, String content) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.content = content;
    }

}
