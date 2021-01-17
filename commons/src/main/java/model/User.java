package model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

@Value
public class User {
    private String tenantId;
    private String partnerId;
    private String userId;

    @JsonCreator
    public User(@JsonProperty("tenantId") String tenantId,
                @JsonProperty("partnerId") String partnerId,
                @JsonProperty("userId") String userId) {
        this.tenantId = Validate.notBlank(tenantId, "TenantId must not be null or blank");
        this.partnerId = partnerId;
        this.userId = Validate.notBlank(userId, "UserId must not be null or blank");
    }

    @JsonIgnore
    public boolean isTenantUser() {
        return StringUtils.isBlank(partnerId);
    }
}
