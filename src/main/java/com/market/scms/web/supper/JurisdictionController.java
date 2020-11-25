package com.market.scms.web.supper;

import com.market.scms.service.PrimaryMenuService;
import com.market.scms.service.StaffPositionRelationService;
import com.market.scms.service.StaffPositionService;
import com.market.scms.service.StaffService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/25 16:21
 */
@CrossOrigin
@RestController
public class JurisdictionController {
    
    @Resource
    private StaffService staffService;
    
    @Resource
    private StaffPositionRelationService staffPositionRelationService;
    
    @Resource
    private StaffPositionService staffPositionService;
    
    @Resource
    private PrimaryMenuService primaryMenuService;
    
    
}
