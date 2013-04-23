package com.scandilabs.catamaran.util;

import java.util.Date;

public interface Timestamped {

    void setCreatedTime(Date created);

    Date getCreatedTime();

    void setLastModifiedTime(Date modified);

    Date getLastModifiedTime();
}
