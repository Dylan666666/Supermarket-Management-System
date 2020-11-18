package com.market.scms.service.impl;

import com.market.scms.entity.ExportBill;
import com.market.scms.enums.ExportBillStatusStateEnum;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.ExportBillMapper;
import com.market.scms.service.ExportBillService;
import com.market.scms.util.ExportBillIdCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 20:10
 */
@Service
public class ExportBillServiceImpl implements ExportBillService {
    
    @Resource
    private ExportBillMapper exportBillMapper;
    
    @Override
    public int insert(ExportBill exportBill, Long couponGoodsId) throws WareHouseManagerException {
        if (exportBill != null && couponGoodsId != null && exportBill.getExportBillCouponId() != null) {
            exportBill.setExportBillStatus(ExportBillStatusStateEnum.START.getState());
            exportBill.setExportBillId(ExportBillIdCreator.get(couponGoodsId));
            try {
                int res = exportBillMapper.insert(exportBill);
                if (res == 0) {
                    throw new WareHouseManagerException("添加失败");
                }
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("添加失败");
            }
        } else {
            throw new WareHouseManagerException("信息不完整，添加失败");
        }
    }

    @Override
    public int update(ExportBill exportBill) throws WareHouseManagerException {
        if (exportBill != null && exportBill.getExportBillId() != null && exportBill.getExportBillStatus() != null) {
            try {
                if (exportBill.getExportBillStatus().equals(ExportBillStatusStateEnum.TO_STOCK.getState())) {
                    exportBill.setExportBillTime(new Date());
                }
                int res = exportBillMapper.update(exportBill);
                if (res == 0) {
                    throw new WareHouseManagerException("信息更改失败");
                }
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("信息更改失败");
            }
        } else {
            throw new WareHouseManagerException("信息不完整，更改失败");
        }
    }

    @Override
    public ExportBill queryByBillId(String id) throws WareHouseManagerException {
        if (id != null) {
            try {
                ExportBill exportBill = exportBillMapper.queryByBillId(id);
                return exportBill;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询失败");
            }
        } else {
            throw new WareHouseManagerException("信息不完整，查询失败");
        }
    }

    @Override
    public ExportBill queryByCouponId(Long couponId) throws WareHouseManagerException {
        if (couponId != null) {
            try {
                ExportBill exportBill = exportBillMapper.queryByCouponId(couponId);
                return exportBill;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询失败");
            }
        } else {
            throw new WareHouseManagerException("信息不完整，查询失败");
        }
    }

    @Override
    public List<ExportBill> queryByBillStatus(int exportBillStatus) throws WareHouseManagerException {
        try {
            List<ExportBill> res = exportBillMapper.queryByBillStatus(exportBillStatus);
            return res;
        } catch (WareHouseManagerException e) {
            throw new WareHouseManagerException("查询失败");
        }
    }

    @Override
    public List<ExportBill> queryAll() throws WareHouseManagerException {
        try {
            List<ExportBill> res = exportBillMapper.queryAll();
            return res;
        } catch (WareHouseManagerException e) {
            throw new WareHouseManagerException("查询失败");
        }
    }
}
