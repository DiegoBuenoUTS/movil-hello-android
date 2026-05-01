/*package com.bueno.helloandroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bueno.helloandroid.databinding.FragmentUserListBinding
import com.bueno.helloandroid.viewmodel.UserViewModel

class UserListFragment : Fragment() {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by activityViewModels()

    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter { user ->
            // Click en un usuario
            viewModel.selectUser(user)
            val action = UserListFragmentDirections
                .actionListToDetail(userId = user.id)
            findNavController().navigate(action)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@UserListFragment.adapter
        }
    }

    private fun setupObservers() {
        viewModel.users.observe(viewLifecycleOwner) { userList ->
            binding.textViewUserCount.text = "Total usuarios: ${userList.size}"
            adapter.submitList(userList)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun setupClickListeners() {
        binding.buttonAddUser.setOnClickListener {
            // Por ahora agregar usuario con datos fijos
            viewModel.addUser(
                name = "Usuario Nuevo ${System.currentTimeMillis() % 100}",
                email = "nuevo${System.currentTimeMillis() % 100}@example.com",
                age = (20..40).random()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}*/