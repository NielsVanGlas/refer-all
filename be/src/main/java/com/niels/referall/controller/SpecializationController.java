package com.niels.referall.controller;

import com.niels.referall.config.exception.BaseException;
import com.niels.referall.config.exception.ValidationException;
import com.niels.referall.dto.pagination.PageShowSpecializationDto;
import com.niels.referall.dto.specialization.ShowSpecializationDto;
import com.niels.referall.service.SpecializationService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/specialization")
public class SpecializationController {

    @Autowired
    private SpecializationService specializationService;

    // CRUD

    // Read All
    @Operation(description = "Get all Specializations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = PageShowSpecializationDto.class))
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
    public ResponseEntity<PageShowSpecializationDto> showRecords(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort
    ) throws Exception {
        page = Common.setPage(page);
        Page<ShowSpecializationDto> pages = specializationService.getSpecializations(Common.getPagination(page, size, sort));
        List<ShowSpecializationDto> response = pages.getContent();
        return response.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(new PageShowSpecializationDto(response, pages.getNumber(), pages.getTotalElements(), pages.getTotalPages()), HttpStatus.OK);

    }

}
