package top.lijingyuan.canal.learning;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * client for canal
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2022-05-02
 * @since 1.0.0
 */
public class CanalClient {

    public static void main(String args[]) throws InvalidProtocolBufferException {
        // 根据ip，直接创建链接，无HA的功能
        String destination = "example";
//        String ip = AddressUtils.getHostIp();
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("127.0.0.1", 11111),
                destination,
                "",
                "");

        while (true) {
            // 连接
            connector.connect();
            // 订阅
            connector.subscribe();
            // 获取数据
            Message message = connector.get(100);
            // 获取 entry 集合
            final List<CanalEntry.Entry> entries = message.getEntries();
            if (entries.isEmpty()) {
                try {
                    System.out.println("没有数据，休息一会儿");
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 遍历 entries
            for (CanalEntry.Entry entry : entries) {
                // 获取表名
                final String tableName = entry.getHeader().getTableName();
                // 获取类型
                final CanalEntry.EntryType entryType = entry.getEntryType();
                // 获取序列化后的数据
                final ByteString storeValue = entry.getStoreValue();
                // 判断当前 entryTyp e类型是否为 ROWDATA
                if (CanalEntry.EntryType.ROWDATA.equals(entryType)) {
                    // 反序列化数据
                    CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(storeValue);

                    // 获取当前事件的操作类型
                    CanalEntry.EventType eventType = rowChange.getEventType();

                    // 获取数据集
                    List<CanalEntry.RowData> rowDataList = rowChange.getRowDatasList();

                    // 遍历rowDataList，并打印数据集
                    for (CanalEntry.RowData rowData : rowDataList) {

                        JSONObject beforeData = new JSONObject();
                        List<CanalEntry.Column> beforeColumnsList = rowData.getBeforeColumnsList();
                        for (CanalEntry.Column column : beforeColumnsList) {
                            beforeData.put(column.getName(), column.getValue());
                        }

                        JSONObject afterData = new JSONObject();
                        List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
                        for (CanalEntry.Column column : afterColumnsList) {
                            afterData.put(column.getName(), column.getValue());
                        }

                        //数据打印
                        System.out.println("Table:" + tableName +
                                ",EventType:" + eventType +
                                ",Before:" + beforeData +
                                ",After:" + afterData);
                    }
                } else {
                    System.out.println("当前操作类型为：" + entryType);
                }
            }
        }
    }

}
