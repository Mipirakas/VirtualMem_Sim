package be.virtualmem.data;

import be.virtualmem.global.address.Address;
import be.virtualmem.global.instruction.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class InstructionReader {
    public static Queue<IInstruction> readFromFile(String path) {
        Queue<IInstruction> instructions = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                IInstruction instruction;
                String[] parts = line.split(" ");
                int pid = Integer.parseInt(parts[0]);

                instruction = switch (parts[1]) {
                    case "START" -> new Start(pid);
                    case "END" -> new End(pid);
                    case "MAP" -> new Map(pid, Address.fromHexToAddress(parts[2]), Integer.parseInt(parts[3]));
                    case "UNMAP" -> new Unmap(pid, Address.fromHexToAddress(parts[2]), Integer.parseInt(parts[3]));
                    case "READ" -> new Read(pid, Address.fromHexToAddress(parts[2]));
                    case "WRITE" -> new Write(pid, Address.fromHexToAddress(parts[2]));
                    default -> throw new IOException("Unknown Instruction: " + parts[1]);
                };

                instructions.add(instruction);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return instructions;
    }
}
