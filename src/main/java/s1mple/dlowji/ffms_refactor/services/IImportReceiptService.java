package s1mple.dlowji.ffms_refactor.services;

import s1mple.dlowji.ffms_refactor.entities.ImportReceipt;

import java.util.List;

public interface IImportReceiptService {
    List<ImportReceipt> getImportReceiptsByQuarter(int quarter, int year);
}
