package com.scandilabs.catamaran.entity.support;

/**
 * Provides convenient default implementations of common java functions for
 * Identifiable entities. Ensures correct behavior in Collections and object
 * comparisons.
 * 
 * @author mkvalsvik
 * 
 */
public abstract class IdentifiableBase implements Identifiable {

    public int hashCode() {
        return 0 != getId() ? Long.valueOf(getId()).hashCode() : "NULL"
                .hashCode();
    }

    public boolean equals(Object obj) {
    	if(this == obj) {
    		return true;
    	}
        if (null == obj  || null == getId()) {
        	return false;
        }

        // Right type of object?
        if (!(obj instanceof IdentifiableBase)) {
            return false;
        }

        return getId().equals(((IdentifiableBase) obj).getId());
    }

    public int compareTo(Object obj) {

        if (null == obj)
            return 1;

        if (obj.getClass().getName().equals(getClass().getName())) {
            Long myId = Long.valueOf(this.getId());
            Long otherId = Long.valueOf(((IdentifiableBase) obj).getId());
            if (null != myId) {
                return myId.compareTo(otherId);
            } else {
                return -1;
            }
        }

        return -1;
    }

    public String toString() {
        return "id=" + this.getId() + ": " + super.toString();
    }
}