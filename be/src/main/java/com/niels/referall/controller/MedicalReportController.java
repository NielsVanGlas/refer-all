package com.niels.referall.controller;

import com.niels.referall.config.exception.BaseException;
import com.niels.referall.config.exception.ValidationException;
import com.niels.referall.dto.medicalReport.CreateMedicalReportDto;
import com.niels.referall.dto.medicalReport.ShowMedicalReportDto;
import com.niels.referall.dto.medicalReport.ShowMedicalReportFileDto;
import com.niels.referall.dto.medicalReport.ShowMedicalReportFullDto;
import com.niels.referall.dto.pagination.PageShowMedicalReportDto;
import com.niels.referall.service.AuthenticationService;
import com.niels.referall.service.MedicalReportService;
import com.niels.referall.util.Common;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/report")
public class MedicalReportController {

    @Autowired
    private MedicalReportService medicalReportService;

    @Autowired
    private AuthenticationService authenticationService;

    //CRUD
    // Create
    @Operation(description = "Create a new Medical Report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UUID.class))
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
    @PostMapping()
    public ResponseEntity<UUID> createRecord(
            @RequestPart("data") CreateMedicalReportDto createMedicalReportDto,
            @RequestPart("file") MultipartFile file,
            Authentication authentication,
            HttpServletRequest request
    ) throws Exception {
        UUID id = medicalReportService.createMedicalReport(createMedicalReportDto, file, authenticationService.getAuthenticatedUser(authentication), request.getRemoteAddr());
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    // Read All
    @Operation(description = "Get all Medical Reports")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PageShowMedicalReportDto.class))
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
    public ResponseEntity<PageShowMedicalReportDto> showRecords(
            Authentication authentication,
            HttpServletRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    ) throws Exception {
        page = Common.setPage(page);
        Page<ShowMedicalReportDto> pages = medicalReportService.getMedicalReports(authenticationService.getAuthenticatedUser(authentication), request.getRemoteAddr(), Common.getPagination(page, size, sort));
        List<ShowMedicalReportDto> response = pages.getContent();
        return response.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(new PageShowMedicalReportDto(response, pages.getNumber(), pages.getTotalElements(), pages.getTotalPages()), HttpStatus.OK);

    }

    // Read One
    @Operation(description = "Get a Medical Report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ShowMedicalReportFullDto.class))
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
    @GetMapping("/{id}")
    public ResponseEntity<ShowMedicalReportFullDto> showRecord(
            Authentication authentication,
            HttpServletRequest request,
            @PathVariable UUID id
    ) throws Exception {
        ShowMedicalReportFullDto record = medicalReportService.getMedicalReport(id, authenticationService.getAuthenticatedUser(authentication), request.getRemoteAddr());
        return new ResponseEntity<ShowMedicalReportFullDto>(record, HttpStatus.OK);
    }

    // Download
    @Operation(description = "Download a Medical Report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ShowMedicalReportFullDto.class))
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
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadRecord(
            Authentication authentication,
            HttpServletRequest request,
            @PathVariable UUID id
    ) throws Exception {
        ShowMedicalReportFileDto report = medicalReportService.getMedicalReportForDownload(id, authenticationService.getAuthenticatedUser(authentication), request.getRemoteAddr());

        String mimeType = report.getMimeType() != null
                ? report.getMimeType()
                : MediaType.APPLICATION_OCTET_STREAM_VALUE;

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + report.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(mimeType))
                .contentLength(report.getFileSize())
                .body(report.getFileContent());
    }

    // Delete
    @Operation(description = "Delete a Bank Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted", content = {
                    @Content
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
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteRecord(
            Authentication authentication,
            HttpServletRequest request,
            @PathVariable UUID id
    ) throws Exception {
        medicalReportService.deleteMedicalReport(authenticationService.getAuthenticatedUser(authentication), id, request.getRemoteAddr());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
