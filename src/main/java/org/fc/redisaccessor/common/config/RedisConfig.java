package org.fc.redisaccessor.common.config;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class RedisConfig {
  private String hostname;
  private Integer port;
  private Integer maxIdle;
  private Integer minIdle;
  private Integer maxWaitMillis;
  private Integer maxTotal;
}
