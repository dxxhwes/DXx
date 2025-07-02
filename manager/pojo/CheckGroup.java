package manager.pojo;

import java.io.Serializable;
import java.util.List;

public class CheckGroup implements Serializable {
    private Integer groupId;        // 检查组ID
    private String groupName;       // 检查组名称
    private String description;     // 描述
    private List<Integer> itemIds;  // 该组包含的检查项ID列表（可选）

    // 无参构造
    public CheckGroup() {}

    // 全参构造
    public CheckGroup(Integer groupId, String groupName, String description, List<Integer> itemIds) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.description = description;
        this.itemIds = itemIds;
    }

    // Getter和Setter
    public Integer getGroupId() { return groupId; }
    public void setGroupId(Integer groupId) { this.groupId = groupId; }

    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Integer> getItemIds() { return itemIds; }
    public void setItemIds(List<Integer> itemIds) { this.itemIds = itemIds; }
}
