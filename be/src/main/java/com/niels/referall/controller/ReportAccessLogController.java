package com.niels.referall.controller;

import com.niels.referall.config.exception.BaseException;
import com.niels.referall.config.exception.ValidationException;
import com.niels.referall.dto.log.ShowReportAccessLogDto;
import com.niels.referall.dto.pagination.PageShowReportAccessLogDto;
import com.niels.referall.service.AuthenticationService;
import com.niels.referall.service.ReportAccessLogService;
import com.niels.referall.util.Common;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/log")
public class ReportAccessLogController {

    @Autowired
    private ReportAccessLogService reportAccessLogService;

    @Autowired
    private AuthenticationService authenticationService;

    //CRUD
    // Read All
    @Operation(description = "Get all Logs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PageShowReportAccessLogDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationException.class))
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseException.class))
            }),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseException.class))
            }),
            @ApiResponse(responseCode = "404", description = "Not Found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseException.class))
            }),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = BaseException.class))
            })
    })
    @GetMapping()
    public ResponseEntity<PageShowReportAccessLogDto> showRecords(
            Authentication authentication,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    ) throws Exception {
        page = Common.setPage(page);
        Page<ShowReportAccessLogDto> pages = reportAccessLogService.getReportAccessLogs(authenticationService.getAuthenticatedUser(authentication), Common.getPagination(page, size, sort));
        List<ShowReportAccessLogDto> response = pages.getContent();
        return response.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(new PageShowReportAccessLogDto(response, pages.getNumber(), pages.getTotalElements(), pages.getTotalPages()), HttpStatus.OK);

    }

}
