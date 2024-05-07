package com.pack.controllers;

import com.pack.dto.CheckDto;
import com.pack.dto.TaskDto;
import com.pack.dto.TaskInvoice;
import com.pack.dto.TaskMapper;
import com.pack.entity.InvoiceFile;
import com.pack.entity.Task;
import com.pack.parser.InvParser;
import com.pack.parser.PdfParser;
import com.pack.repository.InvParserRepository;
import com.pack.repository.InvoiceFileRepository;
import com.pack.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskRepository taskRepository;
    private final InvoiceFileRepository invoiceFileRepository;
    private final InvParserRepository invParserRepository;
    private Integer lastTaskId;
    private final AppService appService;

    @GetMapping("/new")
    public String newTask(Model model) {
        model.addAttribute("task", new TaskDto());
        return "client/new_invoice";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute TaskDto taskDto, Model model) throws IOException {
        System.out.println(taskDto);
        Task task = TaskMapper.toModel(taskDto);
        taskRepository.save(task);
        taskRepository.flush();
        invoiceFileRepository.save(new InvoiceFile(taskDto.getInvoiceScan().getOriginalFilename(), taskDto.getInvoiceScan().getBytes(), task));

        Path path = Paths.get("./file.pdf");
        Files.write(path, taskDto.getInvoiceScan().getBytes());
        InvParser invParser = PdfParser.parseFile(path.toFile());
        System.out.println(invParser);
        invParser.setTask(task);
        invParserRepository.save(invParser);

        lastTaskId = task.getID();
        model.addAttribute("invoice", new TaskInvoice());
        return "client/new_invoice";
    }

    @PostMapping("/add")
    public String addInvoice(@ModelAttribute TaskInvoice taskInvoice, Model model) throws IOException {
        System.out.println(taskInvoice.getFile().getOriginalFilename());
        invoiceFileRepository.save(new InvoiceFile(taskInvoice.getFile().getOriginalFilename(), taskInvoice.getFile().getBytes(),
                taskRepository.findById(lastTaskId).get()));

        Path path = Paths.get("./file.pdf");
        Files.write(path, taskInvoice.getFile().getBytes());
        InvParser invParser = PdfParser.parseFile(path.toFile());
        invParser.setTask(taskRepository.findById(lastTaskId).get());
        System.out.println(invParser);
        invParserRepository.save(invParser);

        model.addAttribute("invoice", new TaskInvoice());
        return "client/new_invoice";
    }

    @PostMapping("/finish_add")
    public String finishAdd(Model model) {
        System.out.println(lastTaskId);
        Task task = taskRepository.findById(lastTaskId).get();
        CheckDto checkDto = new CheckDto(task);
        System.out.println(checkDto.getInvoiceFiles());
        System.out.println(checkDto.getInvParsers());

        model.addAttribute("check", checkDto);

        return "client/new_invoice";
    }

    @PostMapping("/check")
    public String checkTask(@ModelAttribute TaskInvoice taskInvoice, Model model) {
        System.out.println(taskInvoice);
        System.out.println(taskInvoice.getFile().getOriginalFilename());

        return "client/new_invoice";
    }

    @GetMapping(value = "/download/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody byte[] downloadFile(@PathVariable Integer id) {
        return invoiceFileRepository.findById(id).get().getFile();
    }
}













