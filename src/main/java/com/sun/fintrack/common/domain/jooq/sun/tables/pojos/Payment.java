/*
 * This file is generated by jOOQ.
 */
package sun.tables.pojos;


import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Long pmSeq;
    private final String pmCtt;
    private final Long pmPrc;
    private final Long mbSeq;
    private final Long pmCtgId;
    private final LocalDateTime creDt;
    private final LocalDateTime updDt;

    public Payment(Payment value) {
        this.pmSeq = value.pmSeq;
        this.pmCtt = value.pmCtt;
        this.pmPrc = value.pmPrc;
        this.mbSeq = value.mbSeq;
        this.pmCtgId = value.pmCtgId;
        this.creDt = value.creDt;
        this.updDt = value.updDt;
    }

    public Payment(
        Long pmSeq,
        String pmCtt,
        Long pmPrc,
        Long mbSeq,
        Long pmCtgId,
        LocalDateTime creDt,
        LocalDateTime updDt
    ) {
        this.pmSeq = pmSeq;
        this.pmCtt = pmCtt;
        this.pmPrc = pmPrc;
        this.mbSeq = mbSeq;
        this.pmCtgId = pmCtgId;
        this.creDt = creDt;
        this.updDt = updDt;
    }

    /**
     * Getter for <code>fintrack.PAYMENT.PM_SEQ</code>. 결제 일련번호
     */
    public Long getPmSeq() {
        return this.pmSeq;
    }

    /**
     * Getter for <code>fintrack.PAYMENT.PM_CTT</code>. 결제 내용
     */
    public String getPmCtt() {
        return this.pmCtt;
    }

    /**
     * Getter for <code>fintrack.PAYMENT.PM_PRC</code>. 결제 금액
     */
    public Long getPmPrc() {
        return this.pmPrc;
    }

    /**
     * Getter for <code>fintrack.PAYMENT.MB_SEQ</code>. 회원 일련번호
     */
    public Long getMbSeq() {
        return this.mbSeq;
    }

    /**
     * Getter for <code>fintrack.PAYMENT.PM_CTG_ID</code>. 카테고리 아이디
     */
    public Long getPmCtgId() {
        return this.pmCtgId;
    }

    /**
     * Getter for <code>fintrack.PAYMENT.CRE_DT</code>. 등록일시
     */
    public LocalDateTime getCreDt() {
        return this.creDt;
    }

    /**
     * Getter for <code>fintrack.PAYMENT.UPD_DT</code>. 수정일시
     */
    public LocalDateTime getUpdDt() {
        return this.updDt;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Payment other = (Payment) obj;
        if (this.pmSeq == null) {
            if (other.pmSeq != null)
                return false;
        }
        else if (!this.pmSeq.equals(other.pmSeq))
            return false;
        if (this.pmCtt == null) {
            if (other.pmCtt != null)
                return false;
        }
        else if (!this.pmCtt.equals(other.pmCtt))
            return false;
        if (this.pmPrc == null) {
            if (other.pmPrc != null)
                return false;
        }
        else if (!this.pmPrc.equals(other.pmPrc))
            return false;
        if (this.mbSeq == null) {
            if (other.mbSeq != null)
                return false;
        }
        else if (!this.mbSeq.equals(other.mbSeq))
            return false;
        if (this.pmCtgId == null) {
            if (other.pmCtgId != null)
                return false;
        }
        else if (!this.pmCtgId.equals(other.pmCtgId))
            return false;
        if (this.creDt == null) {
            if (other.creDt != null)
                return false;
        }
        else if (!this.creDt.equals(other.creDt))
            return false;
        if (this.updDt == null) {
            if (other.updDt != null)
                return false;
        }
        else if (!this.updDt.equals(other.updDt))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.pmSeq == null) ? 0 : this.pmSeq.hashCode());
        result = prime * result + ((this.pmCtt == null) ? 0 : this.pmCtt.hashCode());
        result = prime * result + ((this.pmPrc == null) ? 0 : this.pmPrc.hashCode());
        result = prime * result + ((this.mbSeq == null) ? 0 : this.mbSeq.hashCode());
        result = prime * result + ((this.pmCtgId == null) ? 0 : this.pmCtgId.hashCode());
        result = prime * result + ((this.creDt == null) ? 0 : this.creDt.hashCode());
        result = prime * result + ((this.updDt == null) ? 0 : this.updDt.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Payment (");

        sb.append(pmSeq);
        sb.append(", ").append(pmCtt);
        sb.append(", ").append(pmPrc);
        sb.append(", ").append(mbSeq);
        sb.append(", ").append(pmCtgId);
        sb.append(", ").append(creDt);
        sb.append(", ").append(updDt);

        sb.append(")");
        return sb.toString();
    }
}
