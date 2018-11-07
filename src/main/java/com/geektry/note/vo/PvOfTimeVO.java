package com.geektry.note.vo;

import java.time.LocalDateTime;

/**
 * @author Chaohang Fu
 */
public class PvOfTimeVO {

    private LocalDateTime samplingTime;
    private Long pv;

    public LocalDateTime getSamplingTime() {
        return samplingTime;
    }

    public void setSamplingTime(LocalDateTime samplingTime) {
        this.samplingTime = samplingTime;
    }

    public Long getPv() {
        return pv;
    }

    public void setPv(Long pv) {
        this.pv = pv;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PvOfTimeVO{");
        sb.append("samplingTime=").append(samplingTime);
        sb.append(", pv=").append(pv);
        sb.append('}');
        return sb.toString();
    }
}
