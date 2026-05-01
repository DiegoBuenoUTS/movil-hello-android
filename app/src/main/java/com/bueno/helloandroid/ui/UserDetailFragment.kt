/*package com.bueno.helloandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bueno.helloandroid.databinding.FragmentUserDetailBinding
import com.bueno.helloandroid.viewmodel.UserViewModel
import androidx.navigation.fragment.navArgs

class UserDetailFragment : Fragment() {

    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by activityViewModels()

    // Recibir argumentos con Safe Args
    //private val args: UserDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cargar usuario específico usando el ID recibido
        loadUserById(args.userId)

        setupObservers()
        setupClickListeners()
    }

    private fun loadUserById(userId: Int) {
        viewModel.users.value?.find { it.id == userId }?.let { user ->
            viewModel.selectUser(user)
        }
    }

    private fun setupObservers() {
        viewModel.users.observe(viewLifecycleOwner) { users ->
            users.find { it.id == args.userId }?.let { user ->
                viewModel.selectUser(user)
            }
        }

        viewModel.selectedUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.textViewName.text = "Nombre: ${it.name}"
                binding.textViewEmail.text = "Email: ${it.email}"
                binding.textViewAge.text = "Edad: ${it.age} años"
                binding.textViewId.text = "ID: ${it.id}"
            }
        }
    }

    private fun setupClickListeners() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}*/