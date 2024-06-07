/*
 * This file is generated by jOOQ.
 */
package sun.tables.pojos;


import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class PaymentCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Long pmCtgId;
    private final String pmCtgNm;

    public PaymentCategory(PaymentCategory value) {
        this.pmCtgId = value.pmCtgId;
        this.pmCtgNm = value.pmCtgNm;
    }

    public PaymentCategory(
        Long pmCtgId,
        String pmCtgNm
    ) {
        this.pmCtgId = pmCtgId;
        this.pmCtgNm = pmCtgNm;
    }

    /**
     * Getter for <code>fintrack.PAYMENT_CATEGORY.PM_CTG_ID</code>. 결제 카테고리 아이디
     */
    public Long getPmCtgId() {
        return this.pmCtgId;
    }

    /**
     * Getter for <code>fintrack.PAYMENT_CATEGORY.PM_CTG_NM</code>. 카테고리명
     */
    public String getPmCtgNm() {
        return this.pmCtgNm;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final PaymentCategory other = (PaymentCategory) obj;
        if (this.pmCtgId == null) {
            if (other.pmCtgId != null)
                return false;
        }
        else if (!this.pmCtgId.equals(other.pmCtgId))
            return false;
        if (this.pmCtgNm == null) {
            if (other.pmCtgNm != null)
                return false;
        }
        else if (!this.pmCtgNm.equals(other.pmCtgNm))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.pmCtgId == null) ? 0 : this.pmCtgId.hashCode());
        result = prime * result + ((this.pmCtgNm == null) ? 0 : this.pmCtgNm.hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PaymentCategory (");

        sb.append(pmCtgId);
        sb.append(", ").append(pmCtgNm);

        sb.append(")");
        return sb.toString();
    }
}
